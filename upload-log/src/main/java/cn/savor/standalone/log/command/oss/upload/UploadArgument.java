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
package cn.savor.standalone.log.command.oss.upload;

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
public class UploadArgument extends AbstractArgument {

    public static final IArgument LogCompressionPackageFrom = new UploadArgument("fromDir", null, null);
    public static final IArgument TempPath = new UploadArgument("tempDir", null, null);
    public static final IArgument BackupPath = new UploadArgument("backupDir", null, null);
    public static final IArgument OSSBucketName = new UploadArgument("bucketName", null, null);
    public static final IArgument OSSKeyPrefix = new UploadArgument("keyPrefix", null, null);
    public static final IArgument AreaUrl = new UploadArgument("areaUrl", null, null);

    public UploadArgument(String name, String nullValue, String[] nullArray) {
        super(name, nullValue, nullArray);
    }
}
