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
package cn.savor.standalone.log.command.oss.download;

import cn.savor.aliyun.oss.IOSSClient;
import cn.savor.aliyun.oss.impl.OSSClientForSavor;
import cn.savor.standalone.log.command.ICommand;
import com.aliyun.oss.model.OSSObjectSummary;
import net.lizhaoweb.common.util.argument.ArgumentFactory;
import net.lizhaoweb.common.util.base.HttpClientSimpleUtil;
import net.lizhaoweb.common.util.base.JsonUtil;
import net.lizhaoweb.common.util.base.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年10月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class CommandDownload implements ICommand {

    private static Pattern pattern = Pattern.compile("(.*)\\[(.*)\\]");

    private String filePath;
    private String ossBucketName;
    private IOSSClient ossClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        String filePath = ArgumentFactory.getParameterValue(B_LogDownloadArgument.FilePath);
        ArgumentFactory.printInputArgument(B_LogDownloadArgument.FilePath, filePath, false);

        String ossBucketName = ArgumentFactory.getParameterValue(B_LogDownloadArgument.OSSBucketName);
        ArgumentFactory.printInputArgument(B_LogDownloadArgument.OSSBucketName, ossBucketName, false);

        String[] ossKeyPrefix = ArgumentFactory.getParameterValues(B_LogDownloadArgument.OSSKeyPrefix);
        ArgumentFactory.printInputArgument(B_LogDownloadArgument.OSSKeyPrefix, Arrays.toString(ossKeyPrefix), false);
        String areaUrl = ArgumentFactory.getParameterValue(B_LogDownloadArgument.AreaUrl);
        ArgumentFactory.printInputArgument(B_LogDownloadArgument.AreaUrl, areaUrl, false);

        // 准备工作
        ArgumentFactory.checkNullValueForArgument(B_LogDownloadArgument.FilePath, filePath);
        ArgumentFactory.checkNullValueForArgument(B_LogDownloadArgument.OSSBucketName, ossBucketName);
        ArgumentFactory.checkNullValueForArgument(B_LogDownloadArgument.OSSKeyPrefix, Arrays.toString(ossKeyPrefix));

        this.ossBucketName = ossBucketName;
        this.filePath = filePath;
        this.ossClient = getOssClient();

        if (StringUtil.isNotBlank(areaUrl)) {
            String json = HttpClientSimpleUtil.get(areaUrl);
            Map<String, Object> data = JsonUtil.toBean(json, Map.class);
            Integer code = (Integer) data.get("code");
            if (code == 10000) {
                String id = ((Map<String, String>) data.get("result")).get("id");
                if (StringUtil.isBlank(id)) throw new IllegalArgumentException("获取城市编码失败，请核查日志文件所属区域是否配置正确");
                for (int i = 0; i < ossKeyPrefix.length; i++) {
                    ossKeyPrefix[i] = StringUtil.replaceOnce(ossKeyPrefix[i], "{}", id);
                }
            } else {
                throw new IllegalArgumentException("获取城市编码失败，请核查日志文件所属区域是否配置正确");
            }
        }

        for (String packagePrefix : ossKeyPrefix) {
            String prefix = packagePrefix;
            Matcher matcher = pattern.matcher(packagePrefix);
            if (matcher.find()) {
                prefix = matcher.group(1);
                String rangeKey = matcher.group(2);
                if (rangeKey.contains("-")) {// 区间
                    String[] keys = rangeKey.split("-");
                    download(prefix, prefix + keys[0], prefix + keys[1]);
                } else {// 多个值
                    String[] keys = rangeKey.split(",");
                    for (String key : keys) {
                        download(prefix + key);
                    }
                }
            } else {
                download(prefix);
            }
        }

    }

    @Override
    public String getInformation() {
        return null;
    }

    /**
     * 下载匹配前缀的所有文件
     *
     * @param prefix
     */
    private void download(String prefix) {
        List<OSSObjectSummary> ossObjectSummaries = ossClient.listObjects(ossBucketName, prefix);
        for (OSSObjectSummary ossObjectSummary : ossObjectSummaries) {
            try {
                String key = ossObjectSummary.getKey();
                Pattern pattern = Pattern.compile(".*log/(box-standalone|box_standalone)/(\\d{1,})/\\d{1,}/(.*)");
                Matcher matcher = pattern.matcher(key);

                if (matcher.find()) {
                    String fileName = matcher.group(3);
                    if (StringUtil.equals(matcher.group(1), "box-standalone")) {
                        fileName = fileName.replaceAll("_.*_", "_" + matcher.group(2) + "_");
                    }
                    String downloadFile = String.format("%s/%s", filePath, fileName);

                    while (downloadFile.contains("\\")) {
                        downloadFile = downloadFile.replace("\\", "/");
                    }
                    while (downloadFile.contains("//")) {
                        downloadFile = downloadFile.replace("//", "/");
                    }
                    ossClient.downloadFile(ossBucketName, key, downloadFile);
                } else {
                    System.out.println("格式不正确文件:" + key);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("\n\n文件下载失败\n\n\n");
            }
        }
    }

    /**
     * 下载匹配前缀且在区间内的文件
     *
     * @param prefix
     * @param start
     * @param end
     */
    private void download(String prefix, String start, String end) {
        List<OSSObjectSummary> ossObjectSummaries = ossClient.listObjects(ossBucketName, prefix);
        for (OSSObjectSummary ossObjectSummary : ossObjectSummaries) {
            String key = ossObjectSummary.getKey();
            if (key.compareTo(start) >= 0 && key.compareTo(end) <= 0) {
                download(key);
            }
        }
    }

    private static IOSSClient getOssClient() {
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI5h1iEI5N7Zjj";
        String secretAccessKey = "m2Mn3HAqhfXZm7o4r9tsUfqrXh2NxE";
        return new OSSClientForSavor(endpoint, accessKeyId, secretAccessKey);
    }
}
