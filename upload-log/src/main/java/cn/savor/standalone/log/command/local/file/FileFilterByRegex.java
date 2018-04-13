/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log.command.local.file
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 13:56
 */
package cn.savor.standalone.log.command.local.file;

import cn.savor.standalone.log.command.AbstractCommand;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h1>过滤器 - 文件</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@RequiredArgsConstructor
public class FileFilterByRegex implements FileFilter {

    @NonNull
    private AbstractCommand command;

    @NonNull
    private boolean isRegexBlank;

    @NonNull
    private Pattern fileNamePattern;

    @Override
    public boolean accept(File pathname) {
        if (!pathname.exists()) {
            command.println("        文件[%s]不存在", pathname);
            return false;
        }
        if (!pathname.isFile()) {
            command.println("        '%s'不是文件", pathname);
            return false;
        }
        if (pathname.length() == 0) {
            command.println("        文件['%s']是0字节", pathname);
            return false;
        }
        if (!isRegexBlank) {
            Matcher matcher = fileNamePattern.matcher(pathname.getName());
            boolean find = matcher.find();
            if (!find) {
                command.println("        文件['%s']不符合要求", pathname);
                return false;
            }
        }
        return true;
    }
}
