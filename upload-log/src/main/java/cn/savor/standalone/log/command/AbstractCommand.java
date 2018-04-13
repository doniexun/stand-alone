/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : {@link cn.savor.standalone.log.command}
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 17:59
 */
package cn.savor.standalone.log.command;

import lombok.NoArgsConstructor;
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
public abstract class AbstractCommand implements ICommand {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private OutputStream outputStream;
    private boolean hasOutputStream;

    public AbstractCommand(OutputStream outputStream) {
        this.outputStream = outputStream;
        hasOutputStream = true;
    }

    protected void write(byte bytes[]) {
        synchronized (this) {
            try {
                if (outputStream == null) {
                    System.out.println(new String(bytes));
                    return;
                }
                outputStream.write(bytes);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    protected void print(String string) {
        synchronized (this) {
            if (hasOutputStream) {
                this.write(string.getBytes());
            } else {
                System.out.print(string);
            }
        }
    }

    protected void print(Object object) {
        synchronized (this) {
            this.print(String.valueOf(object));
        }
    }

    protected void print(String format, Object... args) {
        synchronized (this) {
            String string = String.format(format, args);
            this.print(string);
        }
    }

    public void println(String string) {
        synchronized (this) {
            if (hasOutputStream) {
                this.print(string + "\n");
            } else {
                System.out.println(string);
            }
        }
    }

    public void println(Object object) {
        synchronized (this) {
            this.println(String.valueOf(object));
        }
    }

    public void println(String format, Object... args) {
        synchronized (this) {
            String string = String.format(format, args);
            this.println(string);
        }
    }
}
