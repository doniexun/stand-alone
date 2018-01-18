/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : java
 * @Package : cn.savor.standalone.log.upload
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:17
 */
package cn.savor.standalone.log.upload;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;

import java.io.File;

/**
 * OSS 文件系统
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年09月29日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@RequiredArgsConstructor
public class S_OSSSystem {

    @NonNull
    private OSSClient ossClient;

    public PutObjectResult putObject(String bucketName, String key, File uploadFile) {
        if (StringUtils.isBlank(bucketName)) {
            throw new IllegalArgumentException("OSS bucketName is null");
        }
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("OSS object key is null");
        }
        if (uploadFile == null) {
            throw new IllegalArgumentException("The local-file is null");
        }
        if (!uploadFile.exists()) {
            String message = String.format("The local-file[%s] is not exists", uploadFile);
            throw new IllegalArgumentException(message);
        }
        PutObjectResult putObjectResult = null;
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, uploadFile);
            putObjectResult = ossClient.putObject(putObjectRequest);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
        return putObjectResult;
    }
}
