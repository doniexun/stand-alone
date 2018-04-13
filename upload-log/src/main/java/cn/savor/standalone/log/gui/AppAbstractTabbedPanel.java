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
package cn.savor.standalone.log.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public abstract class AppAbstractTabbedPanel {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    PageContext context;
    OutputStream sourceOutputStream;
    OutputStream messageOutputStream;

    protected Component buildUI(Container parentContainer) {
        JPanel mainPanel = new JPanel();
        mainPanel.setSize(parentContainer.getWidth(), parentContainer.getHeight());
        mainPanel.setLayout(new BorderLayout());

        this.createConfigurePanel(mainPanel);
        this.createOperationPanel(mainPanel);
        this.createMessagePanel(mainPanel);

        return mainPanel;
    }

    void sourcePrintln(String string) {
        try {
            String content = string + "\n";
            sourceOutputStream.write(content.getBytes());
        } catch (IOException e) {
            this.sourcePrintlnError(e);
        }
    }

    void sourcePrintlnError(Exception e) {
        try {
            logger.error(e.getMessage(), e);
            String errorString = String.format("SourceError : %s\n", e.getMessage());
            sourceOutputStream.write(errorString.getBytes());
        } catch (IOException e1) {
            logger.error(e1.getMessage(), e1);
        }
    }

    void messagePrintln(String string) {
        try {
            String content = string + "\n";
            messageOutputStream.write(content.getBytes());
        } catch (IOException e) {
            this.messagePrintlnError(e);
        }
    }

    void messagePrintlnError(Exception e) {
        try {
            logger.error(e.getMessage(), e);
            String errorString = String.format("MessageError : %s\n", e.getMessage());
            messageOutputStream.write(errorString.getBytes());
        } catch (IOException e1) {
            logger.error(e1.getMessage(), e1);
        }
    }

    abstract void createConfigurePanel(JComponent parentPanel);

    abstract void createOperationPanel(JComponent parentPanel);

    abstract void createMessagePanel(JComponent parentPanel);

    abstract void close();
}
