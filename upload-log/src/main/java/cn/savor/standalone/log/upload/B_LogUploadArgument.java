/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : java
 * @Package : cn.savor.standalone.log.upload
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 17:03
 */
package cn.savor.standalone.log.upload;

import net.lizhaoweb.common.util.argument.model.AbstractArgument;
import net.lizhaoweb.common.util.argument.model.IArgument;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年09月29日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class B_LogUploadArgument extends AbstractArgument {

    public static final IArgument LogCompressionPackageFrom = new B_LogUploadArgument("fromDir", null, null);
    public static final IArgument TempPath = new B_LogUploadArgument("tempDir", null, null);
    public static final IArgument BackupPath = new B_LogUploadArgument("backupDir", null, null);
    public static final IArgument OSSBucketName = new B_LogUploadArgument("bucketName", null, null);
    public static final IArgument OSSKeyPrefix = new B_LogUploadArgument("keyPrefix", null, null);
    public static final IArgument AreaUrl = new B_LogUploadArgument("areaUrl", null, null);

    public B_LogUploadArgument(String name, String nullValue, String[] nullArray) {
        super(name, nullValue, nullArray);
    }
}
