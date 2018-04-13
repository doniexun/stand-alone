/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log.command.local.file
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 13:36
 */
package cn.savor.standalone.log.command.local.file;

import cn.savor.standalone.log.command.AbstractCommand;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.OutputStream;

/**
 * <h1>抽象类 - 本地文件操作</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@NoArgsConstructor
public abstract class LocalFileOperation extends AbstractCommand {

    public LocalFileOperation(OutputStream outputStream) {
        super(outputStream);
    }

    /**
     * 当以下情况创建目录：
     * 1、文件/目录不存在
     * 2、文件存在，但不是目录
     *
     * @param directory 目录
     */
    protected void createDirectoryOnNotExistOrNotDirectory(File directory) {
        if (!directory.exists()) {
            boolean isMake = directory.mkdirs();
            if (!isMake) {
                throw new IllegalStateException(String.format("The directory[%s] is not make", directory));
            }
        } else if (!directory.isDirectory()) {
            boolean isDelete = directory.delete();
            if (!isDelete) {
                throw new IllegalStateException(String.format("The directory[%s] is not delete", directory));
            }
            boolean isMake = directory.mkdirs();
            if (!isMake) {
                throw new IllegalStateException(String.format("The directory[%s] is not make", directory));
            }
        }
    }
}
