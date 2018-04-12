/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 17:59
 */
package cn.savor.standalone.log;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <h1>命令 [实现] - 抽象</h1>
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
@RequiredArgsConstructor
public abstract class AbstractCommand implements ICommand {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @NonNull
    private OutputStream outputStream;

    protected void write(byte bytes[]) {
        try {
            if (outputStream == null) {
                return;
            }
            outputStream.write(bytes);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    protected void print(String string) {
        this.write(string.getBytes());
    }

    protected void print(String format, Object... args) {
        String string = String.format(format, args);
        this.print(string);
    }

    protected void println(String format, Object... args) {
        this.print(format + "\n", args);
    }
}
