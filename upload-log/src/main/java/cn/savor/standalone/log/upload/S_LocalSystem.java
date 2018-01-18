/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : java
 * @Package : cn.savor.standalone.log.upload
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:16
 */
package cn.savor.standalone.log.upload;

import java.io.File;

/**
 * 本地文件系统
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年09月29日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class S_LocalSystem {

    public static B_LocalFile loopDirectory(File directory) {
        if (directory == null) {
            throw new IllegalArgumentException("Argument 'directory' is null");
        }
        if (!directory.isDirectory()) {
            String message = String.format("The file[%s] is not a directory in the local system", directory);
            throw new IllegalArgumentException(message);
        }
        if (!directory.exists()) {
            String message = String.format("The directory[%s] is not exists", directory);
            throw new IllegalArgumentException(message);
        }
        B_LocalFile localFile = new B_LocalFile();
        localFile.getDirectoryList().add(directory);

        File[] files = directory.listFiles();
        if (files == null) {
            return localFile;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                B_LocalFile localFileTemp = loopDirectory(file);
                localFile.getFileList().addAll(localFileTemp.getFileList());
                localFile.getDirectoryList().addAll(localFileTemp.getDirectoryList());
            } else if (file.isFile()) {
                localFile.getFileList().add(file);
            }
        }
        return localFile;
    }

    public static B_LocalFile loopDirectory(String directory) {
        return loopDirectory(new File(directory));
    }
}
