/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : java
 * @Package : cn.savor.standalone.ossinspect.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 18:03
 */
package cn.savor.standalone.ossinspect.model;

import lombok.Data;

/**
 * <h1>模型 - 文件信息</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年12月05日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@Data
public class FileInformation {

    /**
     * OSS 键
     */
    private String ossKey;

    /**
     * 机顶盒 MAC
     */
    private String boxMac;

    /**
     * 所属区域
     */
    private String area;

    /**
     * OSS 文件上传时间
     */
    private String eventTime;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件大小
     */
    private long size;

    /**
     * 文件的 MD5
     */
    private String md5;
}
