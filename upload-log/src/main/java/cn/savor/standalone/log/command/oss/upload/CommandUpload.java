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
package cn.savor.standalone.log.command.oss.upload;

import cn.savor.aliyun.oss.IOSSClient;
import cn.savor.aliyun.oss.impl.OSSClientForSavor;
import cn.savor.standalone.log.command.ICommand;
import cn.savor.standalone.log.command.oss.OSSObjectOperation;
import cn.savor.standalone.log.command.oss.upload.bean.LocalFile;
import cn.savor.standalone.log.command.oss.upload.service.LocalSystem;
import cn.savor.standalone.log.util.Constants;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.PutObjectResult;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.NoArgsConstructor;
import net.lizhaoweb.common.util.argument.ArgumentFactory;
import net.lizhaoweb.common.util.base.HttpClientSimpleUtil;
import net.lizhaoweb.common.util.base.HttpUtil;
import net.lizhaoweb.common.util.base.JsonUtil;
import net.lizhaoweb.common.util.base.StringUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.OutputStream;
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
@NoArgsConstructor
public class CommandUpload extends OSSObjectOperation implements ICommand {

    public CommandUpload(OutputStream outputStream) {
        super(outputStream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        String logCompressionPackageFrom = ArgumentFactory.getParameterValue(UploadArgument.LogCompressionPackageFrom);
        ArgumentFactory.printInputArgument(UploadArgument.LogCompressionPackageFrom, logCompressionPackageFrom, false);

        String tempPath = ArgumentFactory.getParameterValue(UploadArgument.TempPath);
        ArgumentFactory.printInputArgument(UploadArgument.TempPath, tempPath, false);

        String backupDir = ArgumentFactory.getParameterValue(UploadArgument.BackupPath);
        ArgumentFactory.printInputArgument(UploadArgument.BackupPath, backupDir, false);

        String ossBucketName = ArgumentFactory.getParameterValue(UploadArgument.OSSBucketName);
        ArgumentFactory.printInputArgument(UploadArgument.OSSBucketName, ossBucketName, false);

        String ossKeyPrefix = ArgumentFactory.getParameterValue(UploadArgument.OSSKeyPrefix);
        ArgumentFactory.printInputArgument(UploadArgument.OSSKeyPrefix, ossKeyPrefix, false);
        String areaUrl = ArgumentFactory.getParameterValue(UploadArgument.AreaUrl);
        ArgumentFactory.printInputArgument(UploadArgument.AreaUrl, areaUrl, false);

        // 准备工作
        ArgumentFactory.checkNullValueForArgument(UploadArgument.LogCompressionPackageFrom, logCompressionPackageFrom);
        ArgumentFactory.checkNullValueForArgument(UploadArgument.BackupPath, backupDir);
        ArgumentFactory.checkNullValueForArgument(UploadArgument.OSSBucketName, ossBucketName);
        ArgumentFactory.checkNullValueForArgument(UploadArgument.OSSKeyPrefix, ossKeyPrefix);

        if (StringUtil.isNotBlank(areaUrl)) {
            String json = HttpClientSimpleUtil.get(areaUrl);
            TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {
            };
            Map<String, Object> response = JsonUtil.toBean(json, typeReference);
            if ("10000".equals(response.get("code"))) {
                Map<String, String> result = (Map<String, String>) response.get("result");
                String id = result.get("id");
                if (StringUtil.isBlank(id)) {
                    throw new IllegalArgumentException("获取城市编码失败，请核查日志文件所属区域是否配置正确");
                }
                ossKeyPrefix = StringUtil.replaceOnce(ossKeyPrefix, "{}", id);
            } else {
                throw new IllegalArgumentException("获取城市编码失败，请核查日志文件所属区域是否配置正确");
            }
        }


        LocalFile localFile = LocalSystem.loopDirectory(logCompressionPackageFrom);

//        List<File> notDeleteFileList = new ArrayList<>();
        List<File> notMoveFileList = new ArrayList<>();
        List<File> notUploadFileList = new ArrayList<>();

        IOSSClient ossClient = this.getOssClient();
        File backupDirectory = new File(backupDir);
        for (File compressionFile : localFile.getFileList()) {
            try {
                // 上传日志文件到 OSS
                String ossKey = HttpUtil.formatPath(String.format("%s/%s", ossKeyPrefix, compressionFile.getName()));
                PutObjectResult putObjectResult = ossClient.putObject(ossBucketName, ossKey, compressionFile);

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
        this.println("/ ------------------------------- 执行结果展示开始 ------------------------------- \\");
        this.println("以下文件上传成功，但移动失败：");
        if (notMoveFileList.size() < 1) {
            this.println(1, "无");
        } else {
            for (File file : notMoveFileList) {
                this.println(1, String.format("[%s] >>> × >>> [%s]", file, backupDirectory));
            }
        }
//        this.println("\n\n以下文件上传成功，但删除失败：");
//        for (File file : notDeleteFileList) {
//            this.println(file);
//        }
        this.println();
        this.println("以下文件操作失败：");
        if (notUploadFileList.size() < 1) {
            this.println(1, "无");
        } else {
            for (File file : notUploadFileList) {
                this.println(1, file);
            }
        }
        this.println("\\ ------------------------------- 执行结果展示结束 ------------------------------- /");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getInformation() {
        StringBuilder information = new StringBuilder();
        information.append("Usage: upload <ARGUMENT>...").append("\n");
        information.append("Command upload argument:").append("\n");
        information.append("    fromDir=<DIR>      Local directory for reading files").append("\n");
        information.append("    tempDir=[DIR]      Local storage temporary directory").append("\n");
        information.append("    backupDir=[DIR]    Local storage backup directory").append("\n");
        information.append("    bucketName=<WORD>  The OSS bucket name for the file to upload").append("\n");
        information.append("    keyPrefix=<WORD>   The prefix of the file in OSS").append("\n");
        information.append("    areaUrl=[WORD]     The url of the area").append("\n");
        return information.toString();
    }

    private IOSSClient getOssClient() {
        ClientConfiguration config = new ClientConfiguration();
        CredentialsProvider credsProvider = new DefaultCredentialProvider(Constants.OSS.CredentialsProvider.ACCESS_KEY_ID, Constants.OSS.CredentialsProvider.SECRET_ACCESS_KEY);

        return new OSSClientForSavor(Constants.OSS.ENDPOINT, credsProvider, config);
    }
}
