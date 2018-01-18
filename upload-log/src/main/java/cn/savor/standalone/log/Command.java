/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : java
 * @Package : cn.savor.standalone.log.upload
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 15:14
 */
package cn.savor.standalone.log;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年10月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Command {
    UPLOAD("upload"),
    DOWNLOAD("download"),
    DECOMPRESS("decompress"),
    CREATEFILE("createfile"),
    UNKNOWN("unknown");


    private String name;

    public static Command fromName(String name) {
        for (Command command : values()) {
            if (command.name.equals(name)) {
                return command;
            }
        }
        return UNKNOWN;
    }
}
