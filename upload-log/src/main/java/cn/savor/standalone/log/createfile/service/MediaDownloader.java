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
package cn.savor.standalone.log.createfile.service;

import cn.savor.aliyun.oss.IOSSClient;
import cn.savor.aliyun.oss.impl.OSSClientForSavor;
import cn.savor.standalone.log.createfile.bean.Item;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectMetadata;
import net.lizhaoweb.common.util.base.FileUtil;
import net.lizhaoweb.common.util.base.StringUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年10月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class MediaDownloader {

    private static String filePath;
    private static String ossBucketName;
    private static String ossKeyPrefix;
    private static IOSSClient ossClient;
    private static String fileName;

    public static List<OSSObjectSummary> execute(List<Item> items, String filePath, String ossBucketName, String ossKeyPrefix) throws IOException {

        // 准备工作
        MediaDownloader.filePath = filePath;
        MediaDownloader.ossBucketName = ossBucketName;
        MediaDownloader.ossKeyPrefix = ossKeyPrefix;
        MediaDownloader.ossClient = getOssClient();

        System.out.println();

        List<OSSObjectSummary> ossObjectSummaryList = ossClient.listObjects(ossBucketName, ossKeyPrefix);
        if (ossObjectSummaryList == null || ossObjectSummaryList.size() < 1) {
            System.out.println("");
        }
        try {


            for (OSSObjectSummary ossObjectSummary : ossObjectSummaryList) {
                if (ossObjectSummary == null) {
                    continue;
                }
                String ossFileKey = ossObjectSummary.getKey();
                if (StringUtil.isBlank(ossFileKey)) {
                    continue;
                }
                if (ossFileKey.indexOf("/") < 0) {
                    continue;
                }
                if (ossFileKey.indexOf(".") < 0) {
                    continue;
                }
                String mediaName = ossFileKey.substring(ossFileKey.lastIndexOf("/") + 1);
                String uploadDateString = null;

                String fileNamed = mediaName.substring(0, mediaName.lastIndexOf("."));
                for (Item item : items) {
                    if (StringUtil.equals(item.getCnaName(), fileNamed)) {
                        if (StringUtil.equals("adv", item.getType())) {
                            fileName = item.getCnaName() + "_" + item.getType();
                        } else {
                            fileName = item.getId() + "_" + item.getType();
                        }

                        //判断文件目录是否存在 如果不存在 创建目录
                        File downloadFile = new File(filePath + "\\" + fileName, item.getId() + "_" + item.getType() + ".ts");
                        if (!downloadFile.getParentFile().exists() || !downloadFile.getParentFile().isDirectory()) {
                            FileUtils.forceMkdir(new File(filePath + "\\" + fileName));
                        }
                        // 已经下载过
                        if (downloadFile.exists()) {
                            continue;
                        }

                        String downloadPath = FileUtil.getCanonicalPath(downloadFile);
                        ObjectMetadata object = ossClient.getObject(ossBucketName, ossFileKey, downloadPath);
//                    ObjectMetadata objectMetadata = ossClient.downloadFile(ossBucketName, ossFileKey, downloadPath);
                        if (object == null) {
                            continue;
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("资源下载失败！！！");
        }
        return ossObjectSummaryList;
    }


    private static IOSSClient getOssClient() {
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI5h1iEI5N7Zjj";
        String secretAccessKey = "m2Mn3HAqhfXZm7o4r9tsUfqrXh2NxE";
        System.out.println(endpoint);
        return new OSSClientForSavor(endpoint, accessKeyId, secretAccessKey);
    }
}
