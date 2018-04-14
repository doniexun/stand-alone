/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : standalone
 * @Package : cn.savor.standalone.log.gui.io
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 21:59
 */
package cn.savor.standalone.log.gui.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <h1>输出流 - 文件域</h1>
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
public abstract class AbstractTextAreaOutputStream extends OutputStream {

    @NonNull
    protected JTextArea textArea;

    @Override
    public void write(int b) throws IOException {

    }

    public void print(String string) {
        synchronized (this) {
            try {
                this.write(string.getBytes());
            } catch (IOException e) {
                this.println(e);
            }
        }
    }

    public void print(Object object) {
        synchronized (this) {
            this.print(String.valueOf(object));
        }
    }

    public void println() {
        this.newLine();
    }

    public void println(String string) {
        synchronized (this) {
            this.print(string);
            this.newLine();
        }
    }

    public void println(Object object) {
        synchronized (this) {
            this.println(String.valueOf(object));
        }
    }

    private void println(Throwable throwable) {
        synchronized (this) {
            try {
                String errorString = String.format("SourceError : %s\n", throwable.getMessage());
                this.write(errorString.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void newLine() {
        synchronized (this) {
            try {
                this.write("\n".getBytes());
            } catch (IOException e) {
                this.println(e);
            }
        }
    }
}
