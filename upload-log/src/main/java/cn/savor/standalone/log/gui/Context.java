/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log.gui
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:56
 */
package cn.savor.standalone.log.gui;

import cn.savor.standalone.log.Configure;
import lombok.Getter;
import nl.demon.shadowland.freedumbytes.swingx.gui.modal.JModalFrame;

import javax.swing.*;
import java.awt.*;

/**
 * <h1>模型 - 上下文</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月09日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class Context {

    private Configure configure;

    @Getter
    private JFrame topWindow;

    @Getter
    private Image icon;

    @Getter
    private JFileChooser fileChooser;

    public Context(Configure configure) {
        this.configure = configure;
        this.icon = new ImageIcon(this.getClass().getResource(WindowConstant.MainFrame.icon)).getImage();

        this.topWindow = new JModalFrame(WindowConstant.MainFrame.title);
        this.topWindow.setIconImage(this.icon);
        this.topWindow.setSize(WindowConstant.MainFrame.width, WindowConstant.MainFrame.height);
        this.topWindow.setResizable(false);
        this.topWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.topWindow.setLocationRelativeTo(null);

        this.fileChooser = new JFileChooser(configure.getUserHome());
    }

    public ItemKeyValue[] getUIData(String key) {
        return configure.getUIData(key);
    }

    public String getUserHome() {
        return configure.getUserHome();
    }

    public String getUserWork() {
        return configure.getUserWork();
    }
}
