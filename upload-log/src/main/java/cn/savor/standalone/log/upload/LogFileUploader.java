/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : java
 * @Package : cn.savor.standalone.log.upload
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 15:49
 */
package cn.savor.standalone.log.upload;

import cn.savor.aliyun.oss.IOSSClient;
import cn.savor.aliyun.oss.impl.OSSClientForSavor;
import com.aliyun.oss.model.PutObjectResult;
import net.lizhaoweb.common.util.argument.ArgumentFactory;
import net.lizhaoweb.common.util.base.HttpClientSimpleUtil;
import net.lizhaoweb.common.util.base.JsonUtil;
import net.lizhaoweb.common.util.base.StringUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年10月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class LogFileUploader {

    public static void execute() {
        String logCompressionPackageFrom = ArgumentFactory.getParameterValue(B_LogUploadArgument.LogCompressionPackageFrom);
        ArgumentFactory.printInputArgument(B_LogUploadArgument.LogCompressionPackageFrom, logCompressionPackageFrom, false);

        String tempPath = ArgumentFactory.getParameterValue(B_LogUploadArgument.TempPath);
        ArgumentFactory.printInputArgument(B_LogUploadArgument.TempPath, tempPath, false);

        String backupDir = ArgumentFactory.getParameterValue(B_LogUploadArgument.BackupPath);
        ArgumentFactory.printInputArgument(B_LogUploadArgument.BackupPath, backupDir, false);

        String ossBucketName = ArgumentFactory.getParameterValue(B_LogUploadArgument.OSSBucketName);
        ArgumentFactory.printInputArgument(B_LogUploadArgument.OSSBucketName, ossBucketName, false);

        String ossKeyPrefix = ArgumentFactory.getParameterValue(B_LogUploadArgument.OSSKeyPrefix);
        ArgumentFactory.printInputArgument(B_LogUploadArgument.OSSKeyPrefix, ossKeyPrefix, false);
        String areaUrl = ArgumentFactory.getParameterValue(B_LogUploadArgument.AreaUrl);
        ArgumentFactory.printInputArgument(B_LogUploadArgument.AreaUrl, areaUrl, false);

        // 准备工作
        ArgumentFactory.checkNullValueForArgument(B_LogUploadArgument.LogCompressionPackageFrom, logCompressionPackageFrom);
        ArgumentFactory.checkNullValueForArgument(B_LogUploadArgument.BackupPath, backupDir);
        ArgumentFactory.checkNullValueForArgument(B_LogUploadArgument.OSSBucketName, ossBucketName);
        ArgumentFactory.checkNullValueForArgument(B_LogUploadArgument.OSSKeyPrefix, ossKeyPrefix);

        if (StringUtil.isNotBlank(areaUrl)) {
            String json = HttpClientSimpleUtil.get(areaUrl);
            Map<String, Object> data = JsonUtil.toBean(json, Map.class);
            Integer code = (Integer) data.get("code");
            if (code == 10000) {
                String id = ((Map<String, String>) data.get("result")).get("id");
                if (StringUtil.isBlank(id)) throw new IllegalArgumentException("获取城市编码失败，请核查日志文件所属区域是否配置正确");
                ossKeyPrefix = StringUtil.replaceOnce(ossKeyPrefix, "{}", id);
            } else {
                throw new IllegalArgumentException("获取城市编码失败，请核查日志文件所属区域是否配置正确");
            }
        }


        B_LocalFile localFile = S_LocalSystem.loopDirectory(logCompressionPackageFrom);

//        List<File> notDeleteFileList = new ArrayList<>();
        List<File> notMoveFileList = new ArrayList<>();
        List<File> notUploadFileList = new ArrayList<>();

        IOSSClient ossClient = getOssClient();
        File backupDirectory = new File(backupDir);
        for (File compressionFile : localFile.getFileList()) {
            try {
                File uploadFile = compressionFile;

                // 上传日志文件到 OSS
                String ossKey = String.format("%s/%s", ossKeyPrefix, uploadFile.getName());
                while (ossKey.contains("\\")) {
                    ossKey = ossKey.replace("\\", "/");
                }
                while (ossKey.contains("//")) {
                    ossKey = ossKey.replace("//", "/");
                }
                PutObjectResult putObjectResult = ossClient.putObject(ossBucketName, ossKey, uploadFile);

                // 上传 OSS 成功后，删除本地文件
                String ossFileETag = putObjectResult.getETag();
                if (StringUtils.isNotBlank(ossFileETag)) {
                    try {
                        FileUtils.moveFileToDirectory(compressionFile, backupDirectory, true);
                    } catch (Exception e) {
                        notMoveFileList.add(compressionFile);
                    }
//                    if (!compressionFile.delete()) {
//                        notDeleteFileList.add(compressionFile);
//                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                notUploadFileList.add(compressionFile);
            }
        }

        // 文件操作失败提示
        System.out.println("\n\n以下文件上传成功，但移动失败：");
        for (File file : notMoveFileList) {
            System.out.println(String.format("[%s] >>> × >>> [%s]", file, backupDirectory));
        }
//        System.out.println("\n\n以下文件上传成功，但删除失败：");
//        for (File file : notDeleteFileList) {
//            System.out.println(file);
//        }
        System.out.println("\n\n以下文件操作失败：");
        for (File file : notUploadFileList) {
            System.out.println(file);
        }
        System.out.println("\n\n全部文件已经上传到 OSS 完毕\n\n\n");
    }

    private static IOSSClient getOssClient() {
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI5h1iEI5N7Zjj";
        String secretAccessKey = "m2Mn3HAqhfXZm7o4r9tsUfqrXh2NxE";
        return new OSSClientForSavor(endpoint, accessKeyId, secretAccessKey);
    }

}
