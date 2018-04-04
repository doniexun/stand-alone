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
import javax.swing.border.TitledBorder;
import java.awt.*;

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

    private JModalFrame mainFrame;

    public void init() {
        mainFrame = new JModalFrame(WindowConstant.MainFrame.title);
        mainFrame.setSize(WindowConstant.MainFrame.width, WindowConstant.MainFrame.height);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setSize(mainFrame.getWidth(), mainFrame.getHeight());
        mainPanel.setLayout(new BorderLayout());

        this.createConfigurePanel(mainPanel);
        this.createOperationPanel(mainPanel);
        this.createMessagePanel(mainPanel);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    private void createConfigurePanel(JPanel parentPanel) {
        TitledBorder configurePanelTitledBorder = new TitledBorder(WindowConstant.ConfigurePanel.title);
        JPanel configurePanel = new JPanel();
//        configurePanel.setLayout(null);
        configurePanel.setPreferredSize(new Dimension(parentPanel.getWidth(), parentPanel.getHeight() * 3 / 5 - 80));
        configurePanel.setBorder(configurePanelTitledBorder);
        parentPanel.add(configurePanel, BorderLayout.NORTH);
    }

    private void createOperationPanel(JPanel parentPanel) {
        TitledBorder configurePanelTitledBorder = new TitledBorder(WindowConstant.OperationPanel.title);
        JPanel configurePanel = new JPanel();
//        configurePanel.setLayout(null);
        configurePanel.setPreferredSize(new Dimension(parentPanel.getWidth(), 80));
        configurePanel.setBorder(configurePanelTitledBorder);
        parentPanel.add(configurePanel, BorderLayout.CENTER);
    }

    private void createMessagePanel(JPanel parentPanel) {
        TitledBorder configurePanelTitledBorder = new TitledBorder(WindowConstant.MessagePanel.title);
        JPanel configurePanel = new JPanel();
//        configurePanel.setLayout(null);
        configurePanel.setPreferredSize(new Dimension(parentPanel.getWidth(), parentPanel.getHeight() * 2 / 5));
        configurePanel.setBorder(configurePanelTitledBorder);
        parentPanel.add(configurePanel, BorderLayout.SOUTH);
    }

    public void destroy() {
        System.out.println("window.destroy");
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.init();
        window.destroy();
    }
}
