/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : {@link cn.savor.standalone.log.command}
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 15:14
 */
package cn.savor.standalone.log.command;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h1>工厂 - 命令</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月09日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class CommandFactory {

    private static Map<String, ICommand> COMMAND_MAP = new ConcurrentHashMap<>();

    public static void putCommand(String commandName, ICommand command) {
        COMMAND_MAP.put(commandName, command);
    }

    public static ICommand getCommand(String commandName) {
        return COMMAND_MAP.get(commandName);
    }

    public static Set<String> commandNames() {
        return COMMAND_MAP.keySet();
    }
}
