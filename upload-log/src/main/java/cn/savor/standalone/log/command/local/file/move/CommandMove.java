/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : {@link cn.savor.standalone.log.command.local.file.copy}
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:40
 */
package cn.savor.standalone.log.command.local.file.move;

import cn.savor.standalone.log.command.ICommand;
import cn.savor.standalone.log.command.local.file.FileFilterByRegex;
import cn.savor.standalone.log.command.local.file.LocalFileOperation;
import lombok.NoArgsConstructor;
import net.lizhaoweb.common.util.argument.ArgumentFactory;
import net.lizhaoweb.common.util.base.FileUtil;
import net.lizhaoweb.common.util.base.StringUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.OutputStream;
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
public class CommandMove extends LocalFileOperation implements ICommand {

    public CommandMove(OutputStream outputStream) {
        super(outputStream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() throws Exception {
        String[] sourceDirectoryPathArray = ArgumentFactory.getParameterValues(MoveArgument.sourceDirectory);
        ArgumentFactory.printInputArgument(MoveArgument.sourceDirectory, sourceDirectoryPathArray);

        String targetDirectoryPath = ArgumentFactory.getParameterValue(MoveArgument.targetDirectory);
        ArgumentFactory.printInputArgument(MoveArgument.targetDirectory, targetDirectoryPath, false);

        final String regex = ArgumentFactory.getParameterValue(MoveArgument.regex);
        ArgumentFactory.printInputArgument(MoveArgument.regex, regex, false);

        ArgumentFactory.checkNullValuesForArgument(MoveArgument.sourceDirectory, sourceDirectoryPathArray);
        ArgumentFactory.checkNullValueForArgument(MoveArgument.targetDirectory, targetDirectoryPath);
        final boolean isRegexBlank = StringUtil.isBlank(regex);

        File targetDirectory = new File(targetDirectoryPath);
        this.createDirectoryOnNotExistOrNotDirectory(targetDirectory);

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
            FileFilter fileFilter = new FileFilterByRegex(this, isRegexBlank, fileNamePattern);
            FileUtil.moveDirectory(sourceDirectory, targetDirectory, fileFilter);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getInformation() {
        StringBuilder information = new StringBuilder();
        information.append("Usage: move <ARGUMENT>...").append("\n");
        information.append("Command move argument:").append("\n");
        information.append("    srcDir=<DIR>      The directory for copying files.More than one can be configured.").append("\n");
        information.append("    tarDir=<DIR>      The directory for copied files.").append("\n");
        information.append("    regex=[REGEX]     Filter the regular expression from the source directory.").append("\n");
        return information.toString();
    }
}
