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
import cn.savor.standalone.log.command.oss.OSSObjectOperation;
import cn.savor.standalone.log.util.Constants;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.OSSObjectSummary;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.NoArgsConstructor;
import net.lizhaoweb.common.util.argument.ArgumentFactory;
import net.lizhaoweb.common.util.base.HttpClientSimpleUtil;
import net.lizhaoweb.common.util.base.JsonUtil;
import net.lizhaoweb.common.util.base.StringUtil;

import java.io.OutputStream;
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
@NoArgsConstructor
public class CommandDownload extends OSSObjectOperation implements ICommand {

    private static Pattern pattern = Pattern.compile("(.*)\\[(.*)\\]");

    public CommandDownload(OutputStream outputStream) {
        super(outputStream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        String downloadPath = ArgumentFactory.getParameterValue(DownloadArgument.LogCompressionPackageTo);
        ArgumentFactory.printInputArgument(DownloadArgument.LogCompressionPackageTo, downloadPath, false);

        String tempPath = ArgumentFactory.getParameterValue(DownloadArgument.TempPath);
        ArgumentFactory.printInputArgument(DownloadArgument.TempPath, tempPath, false);

        String backupDir = ArgumentFactory.getParameterValue(DownloadArgument.BackupPath);
        ArgumentFactory.printInputArgument(DownloadArgument.BackupPath, backupDir, false);

        String ossBucketName = ArgumentFactory.getParameterValue(DownloadArgument.OSSBucketName);
        ArgumentFactory.printInputArgument(DownloadArgument.OSSBucketName, ossBucketName, false);

        String[] ossKeyPrefix = ArgumentFactory.getParameterValues(DownloadArgument.OSSKeyPrefix);
        ArgumentFactory.printInputArgument(DownloadArgument.OSSKeyPrefix, Arrays.toString(ossKeyPrefix), false);

        String areaUrl = ArgumentFactory.getParameterValue(DownloadArgument.AreaUrl);
        ArgumentFactory.printInputArgument(DownloadArgument.AreaUrl, areaUrl, false);

        // 准备工作
        ArgumentFactory.checkNullValueForArgument(DownloadArgument.LogCompressionPackageTo, downloadPath);
        ArgumentFactory.checkNullValueForArgument(DownloadArgument.OSSBucketName, ossBucketName);
        ArgumentFactory.checkNullValuesForArgument(DownloadArgument.OSSKeyPrefix, ossKeyPrefix);


        IOSSClient ossClient = this.getOssClient();

        if (StringUtil.isNotBlank(areaUrl)) {
            String json = HttpClientSimpleUtil.get(areaUrl);
            TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {
            };
            Map<String, Object> response = JsonUtil.toBean(json, typeReference);
            Integer responseCode = (Integer) response.get("code");
            if (10000 == responseCode) {
                Map<String, String> result = (Map<String, String>) response.get("result");
                String id = result.get("id");
                if (StringUtil.isBlank(id)) {
                    throw new IllegalArgumentException("获取城市编码失败，请核查日志文件所属区域是否配置正确");
                }
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
                    this.download(ossClient, ossBucketName, prefix, downloadPath, prefix + keys[0], prefix + keys[1]);
                } else {// 多个值
                    String[] keys = rangeKey.split(",");
                    for (String key : keys) {
                        this.download(ossClient, ossBucketName, prefix + key, downloadPath);
                    }
                }
            } else {
                this.download(ossClient, ossBucketName, prefix, downloadPath);
            }
        }
    }

    @Override
    public String getInformation() {
        StringBuilder information = new StringBuilder();
        information.append("Usage: download <ARGUMENT>...").append("\n");
        information.append("Command download argument:").append("\n");
        information.append("    downloadDir=<DIR>    Local directory for writing files").append("\n");
        information.append("    tempDir=[DIR]        Local storage temporary directory").append("\n");
        information.append("    bucketName=<WORD>    The OSS bucket name for the file to upload").append("\n");
        information.append("    keyPrefix=<WORD...>  The prefix of the file in OSS").append("\n");
        return information.toString();
    }

    /**
     * 下载匹配前缀的所有文件
     *
     * @param ossClient     OSS 客户端
     * @param ossBucketName OSS 桶名
     * @param prefix        OSS 对象前缀
     * @param downloadPath  保存路径
     */
    private void download(IOSSClient ossClient, String ossBucketName, String prefix, String downloadPath) {
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
                    String downloadFile = String.format("%s/%s", downloadPath, fileName);

                    while (downloadFile.contains("\\")) {
                        downloadFile = downloadFile.replace("\\", "/");
                    }
                    while (downloadFile.contains("//")) {
                        downloadFile = downloadFile.replace("//", "/");
                    }
                    ossClient.downloadFile(ossBucketName, key, downloadFile);
                } else {
                    this.println("格式不正确文件:" + key);
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.println("\n\n文件下载失败\n\n\n");
            }
        }
    }

    /**
     * 下载匹配前缀且在区间内的文件
     *
     * @param ossClient     OSS 客户端
     * @param ossBucketName OSS 桶名
     * @param prefix        OSS 对象前缀
     * @param downloadPath  保存路径
     * @param start         开始
     * @param end           结束
     */
    private void download(IOSSClient ossClient, String ossBucketName, String prefix, String downloadPath, String start, String end) {
        List<OSSObjectSummary> ossObjectSummaries = ossClient.listObjects(ossBucketName, prefix);
        for (OSSObjectSummary ossObjectSummary : ossObjectSummaries) {
            String key = ossObjectSummary.getKey();
            if (key.compareTo(start) >= 0 && key.compareTo(end) <= 0) {
                this.download(ossClient, ossBucketName, key, downloadPath);
            }
        }
    }

    private IOSSClient getOssClient() {
        ClientConfiguration config = new ClientConfiguration();
        CredentialsProvider credsProvider = new DefaultCredentialProvider(Constants.OSS.CredentialsProvider.ACCESS_KEY_ID, Constants.OSS.CredentialsProvider.SECRET_ACCESS_KEY);

        return new OSSClientForSavor(Constants.OSS.ENDPOINT, credsProvider, config);
    }
}
