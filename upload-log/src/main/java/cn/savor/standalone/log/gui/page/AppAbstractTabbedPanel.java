/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log.gui
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 15:56
 */
package cn.savor.standalone.log.gui.page;

import cn.savor.standalone.log.gui.bean.PageContext;
import cn.savor.standalone.log.gui.io.AbstractTextAreaOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * <h1>应用页面 - 抽象类</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public abstract class AppAbstractTabbedPanel {
    static final String INDENT = "        ";

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected PageContext context;
    protected AbstractTextAreaOutputStream sourceOutputStream;
    protected AbstractTextAreaOutputStream messageOutputStream;

    public Component buildUI(Container parentContainer) {
        JPanel mainPanel = new JPanel();
        mainPanel.setSize(parentContainer.getWidth(), parentContainer.getHeight());
        mainPanel.setLayout(new BorderLayout());

        this.createConfigurePanel(mainPanel);
        this.createOperationPanel(mainPanel);
        this.createMessagePanel(mainPanel);

        return mainPanel;
    }

    void sourcePrintln(String string) {
        synchronized (this) {
            sourceOutputStream.println(string);
        }
    }

    void sourcePrintln(String format, Object... args) {
        synchronized (this) {
            String string = String.format(format, args);
            sourceOutputStream.println(string);
        }
    }

    void sourcePrintln(Object object) {
        synchronized (this) {
            sourceOutputStream.println(object);
        }
    }

    void messageNewLine() {
        synchronized (this) {
            messageOutputStream.println();
        }
    }

    void messagePrintln(String string) {
        synchronized (this) {
            messageOutputStream.println(string);
        }
    }

    void messagePrintln(int indent, String string) {
        synchronized (this) {
            StringBuffer indentString = new StringBuffer();
            for (int count = 0; count < indent; count++) {
                indentString.append(INDENT);
            }
            indentString.append(string);
            this.messagePrintln(indentString);
        }
    }

    void messagePrintln(int indent, String format, Object... args) {
        synchronized (this) {
            String string = String.format(format, args);
            this.messagePrintln(indent, string);
        }
    }

    void messagePrintln(String format, Object... args) {
        synchronized (this) {
            String string = String.format(format, args);
            messageOutputStream.println(string);
        }
    }

    void messagePrintln(Object object) {
        synchronized (this) {
            messageOutputStream.println(object);
        }
    }

    void messagePrintlnError(Throwable throwable) {
        synchronized (this) {
            try {
                logger.error(throwable.getMessage(), throwable);
//            String errorString = String.format("MessageError : %s\n", e.getMessage());
                messageOutputStream.println(throwable);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    abstract void createConfigurePanel(JComponent parentPanel);

    abstract void createOperationPanel(JComponent parentPanel);

    abstract void createMessagePanel(JComponent parentPanel);

    abstract void close();
}
