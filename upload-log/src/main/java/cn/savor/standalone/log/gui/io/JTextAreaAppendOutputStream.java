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

import javax.swing.*;
import java.io.IOException;

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
public class JTextAreaAppendOutputStream extends AbstractTextAreaOutputStream {

    public JTextAreaAppendOutputStream(JTextArea textArea) {
        super(textArea);
    }

    @Override
    public void write(byte[] bytes, int offset, int length) throws IOException {
        synchronized (this) {
            textArea.append(new String(bytes, offset, length));
        }
    }
}
