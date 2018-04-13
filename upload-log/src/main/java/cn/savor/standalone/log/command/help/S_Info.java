/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : java
 * @Package : cn.savor.standalone.log.help
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 15:48
 */
package cn.savor.standalone.log.command.help;

import cn.savor.standalone.log.command.CommandFactory;

import java.util.Set;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年10月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class S_Info {

    // 帮助说明
    public static void helpDescription() {
        Set<String> commandNameSet = CommandFactory.commandNames();
        for (String commandName : commandNameSet) {
            System.out.println(commandName);
        }
    }
}
