/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log.gui
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 17:01
 */
package cn.savor.standalone.log.gui;

import nl.demon.shadowland.freedumbytes.swingx.gui.modal.JModalFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月04日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class MainWindow {

    public void init() throws IOException {
        this.buildWindow();
    }

    /**
     * 构建窗口
     */
    private void buildWindow() {
        Image icon = new ImageIcon(this.getClass().getResource(WindowConstant.MainFrame.icon)).getImage();
        JModalFrame mainFrame = new JModalFrame(WindowConstant.MainFrame.title);
        mainFrame.setIconImage(icon);
        mainFrame.setSize(WindowConstant.MainFrame.width, WindowConstant.MainFrame.height);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setSize(mainFrame.getWidth(), mainFrame.getHeight());

        new AppConfigTabbedPane().buildUI(tabbedPane);
        new AppMainTabbedPane().buildUI(tabbedPane);

        mainFrame.add(tabbedPane);
        mainFrame.setVisible(true);
    }

    public void destroy() {
        System.out.println("window.destroy");
    }

    public static void main(String[] args) {
        try {
            MainWindow window = new MainWindow();
            window.init();
            window.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
