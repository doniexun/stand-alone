/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log.file.copy
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:40
 */
package cn.savor.standalone.log.file.copy;

import cn.savor.standalone.log.AbstractCommand;
import cn.savor.standalone.log.ICommand;
import lombok.NoArgsConstructor;
import net.lizhaoweb.common.util.argument.ArgumentFactory;
import net.lizhaoweb.common.util.base.FileUtil;
import net.lizhaoweb.common.util.base.StringUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h1>命令 [实现] - 复制</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月12日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@NoArgsConstructor
public class CommandCopy extends AbstractCommand implements ICommand {

    public CommandCopy(OutputStream outputStream) {
        super(outputStream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() throws Exception {
        String[] sourceDirectoryPathArray = ArgumentFactory.getParameterValues(CopyArgument.sourceDirectory);
        ArgumentFactory.printInputArgument(CopyArgument.sourceDirectory, sourceDirectoryPathArray);

        String targetDirectoryPath = ArgumentFactory.getParameterValue(CopyArgument.targetDirectory);
        ArgumentFactory.printInputArgument(CopyArgument.targetDirectory, targetDirectoryPath, false);

        final String regex = ArgumentFactory.getParameterValue(CopyArgument.regex);
        ArgumentFactory.printInputArgument(CopyArgument.regex, regex, false);

        ArgumentFactory.checkNullValuesForArgument(CopyArgument.sourceDirectory, sourceDirectoryPathArray);
        ArgumentFactory.checkNullValueForArgument(CopyArgument.targetDirectory, targetDirectoryPath);
        final boolean isRegexBlank = StringUtil.isBlank(regex);

        File targetDirectory = new File(targetDirectoryPath);
        if (!targetDirectory.exists()) {
            boolean isMake = targetDirectory.mkdirs();
            if (!isMake) {
                throw new IllegalStateException(String.format("The directory[%s] is not make", targetDirectory));
            }
        } else if (!targetDirectory.isDirectory()) {
            boolean isDelete = targetDirectory.delete();
            if (!isDelete) {
                throw new IllegalStateException(String.format("The directory[%s] is not delete", targetDirectory));
            }
            boolean isMake = targetDirectory.mkdirs();
            if (!isMake) {
                throw new IllegalStateException(String.format("The directory[%s] is not make", targetDirectory));
            }
        }

        Pattern fileNamePattern = null;
        if (!isRegexBlank) {
            fileNamePattern = Pattern.compile(regex);
        }
        for (String sourceDirectoryPath : sourceDirectoryPathArray) {
            File sourceDirectory = new File(sourceDirectoryPath);
            if (!sourceDirectory.exists()) {
                this.println("        源目录[%s]不存在", sourceDirectory);
                continue;
            }
            if (!sourceDirectory.isDirectory()) {
                this.println("        '%s'不是目录", sourceDirectory);
                continue;
            }
            final Pattern finalFileNamePattern = fileNamePattern;
            FileFilter fileFilter = new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (!pathname.exists()) {
                        println("        文件[%s]不存在", pathname);
                        return false;
                    }
                    if (!pathname.isFile()) {
                        println("        '%s'不是文件", pathname);
                        return false;
                    }
                    if (pathname.length() == 0) {
                        println("        文件['%s']是0字节", pathname);
                        return false;
                    }
                    if (!isRegexBlank) {
                        Matcher matcher = finalFileNamePattern.matcher(pathname.getName());
                        boolean find = matcher.find();
                        if (!find) {
                            println("        文件['%s']不符合要求", pathname);
                            return false;
                        }
                    }
                    return true;
                }
            };
            FileUtil.copyDirectory(sourceDirectory, targetDirectory, fileFilter);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getInformation() {
        StringBuilder information = new StringBuilder();
        information.append("Usage: copy <ARGUMENT>...").append("\n");
        information.append("Command upload argument:").append("\n");
        information.append("    srcDir=DIR      The directory for copying files.More than one can be configured.").append("\n");
        information.append("    tarDir=DIR      The directory for copied files.").append("\n");
        return information.toString();
    }
}
