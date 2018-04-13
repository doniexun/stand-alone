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
package cn.savor.standalone.log.gui.bean;

import cn.savor.standalone.log.model.Configure;
import cn.savor.standalone.log.util.Constants;
import cn.savor.standalone.log.gui.util.WindowConstant;
import cn.savor.standalone.log.model.ItemKeyValue;
import lombok.Getter;
import nl.demon.shadowland.freedumbytes.swingx.gui.modal.JModalFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;
import java.util.Set;

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
public class ApplicationContext {

    private Configure configure;

    @Getter
    private JFrame topWindow;

    @Getter
    private Image icon;

    @Getter
    private JFileChooser fileChooser;

    @Getter
    private int status;

    public ApplicationContext(Configure configure) {
        this.status = Constants.Server.Status.STARTING;
        this.configure = configure;
        this.icon = new ImageIcon(this.getClass().getResource(WindowConstant.MainFrame.icon)).getImage();

        this.topWindow = new JModalFrame(WindowConstant.MainFrame.title);
        this.topWindow.setIconImage(this.icon);
        this.topWindow.setSize(WindowConstant.MainFrame.width, WindowConstant.MainFrame.height);
        this.topWindow.setResizable(false);
//        this.topWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.topWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.topWindow.setLocationRelativeTo(null);
        topWindow.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                status = Constants.Server.Status.RUNNING;
            }

            @Override
            public void windowClosing(WindowEvent e) {
                status = Constants.Server.Status.STOPPING;
            }

            @Override
            public void windowClosed(WindowEvent e) {
                status = Constants.Server.Status.STOPPED;
            }

            @Override
            public void windowIconified(WindowEvent e) {
//                System.out.println("windowIconified 3");
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
//                System.out.println("windowDeiconified 5");
            }

            @Override
            public void windowActivated(WindowEvent e) {
//                System.out.println("windowActivated 1");
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
//                System.out.println("windowDeactivated 4");
            }
        });

        this.fileChooser = new JFileChooser();
    }

    public ItemKeyValue[] getUIData(String key) {
        return configure.getUIData(key);
    }

    public void setConfig(String name, String value) {
        this.configure.putConfig(name, value);
    }

    public String getConfig(String name) {
        return this.configure.getConfig(name);
    }

    public String getCurrentWorkDirectoryPath() {
        return configure.getCurrentWorkDirectoryPath();
    }

    public String getWorkDirectoryPath() {
        return configure.getWorkDirectoryPath();
    }

    public String getConfigFilePath() {
        return configure.getConfigFilePath();
    }

    public Set<Map.Entry<String, String>> getConfigs() {
        return configure.getConfigs();
    }
}
