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

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月05日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@RequiredArgsConstructor
class AppMainTabbedPanel {

    @NonNull
    private ApplicationContext context;

    Component buildUI(Container parentContainer) {
        JPanel mainPanel = new JPanel();
        mainPanel.setSize(parentContainer.getWidth(), parentContainer.getHeight());
        mainPanel.setLayout(new BorderLayout());

        this.createConfigurePanel(mainPanel);
        this.createOperationPanel(mainPanel);
        this.createMessagePanel(mainPanel);

        parentContainer.add(mainPanel, WindowConstant.AppTabbedPanel.Main.title);
        return mainPanel;
    }

    private void createConfigurePanel(JComponent parentPanel) {
        TitledBorder configurePanelTitledBorder = new TitledBorder(WindowConstant.AppTabbedPanel.Main.ConfigurePanel.title);
        JPanel configurePanel = new JPanel();
        configurePanel.setPreferredSize(new Dimension(parentPanel.getWidth(), parentPanel.getHeight() * 2 / 5));
        configurePanel.setSize(parentPanel.getWidth(), parentPanel.getHeight() * 2 / 5);
        configurePanel.setBorder(configurePanelTitledBorder);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension((int) (configurePanel.getWidth() * 0.97), (int) (configurePanel.getHeight() * 0.89)));
        scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        JTextArea textArea = new JTextArea("aaaa\nbbb");
        textArea.setBorder(new EmptyBorder(0, 0, 0, 0));
        textArea.setEnabled(false);
        scrollPane.setViewportView(textArea);

        configurePanel.add(scrollPane);
        parentPanel.add(configurePanel, BorderLayout.NORTH);
    }

    private void createOperationPanel(JComponent parentPanel) {
        TitledBorder configurePanelTitledBorder = new TitledBorder(WindowConstant.AppTabbedPanel.Main.OperationPanel.title);
        JPanel configurePanel = new JPanel();
//        configurePanel.setLayout(null);
        configurePanel.setPreferredSize(new Dimension(parentPanel.getWidth(), 80));
        configurePanel.setBorder(configurePanelTitledBorder);
        parentPanel.add(configurePanel, BorderLayout.CENTER);
    }

    private void createMessagePanel(JComponent parentPanel) {
        TitledBorder configurePanelTitledBorder = new TitledBorder(WindowConstant.AppTabbedPanel.Main.MessagePanel.title);
        JPanel configurePanel = new JPanel();
//        configurePanel.setLayout(null);
        configurePanel.setPreferredSize(new Dimension(parentPanel.getWidth(), parentPanel.getHeight() * 2 / 5));
        configurePanel.setBorder(configurePanelTitledBorder);
        parentPanel.add(configurePanel, BorderLayout.SOUTH);
    }
}
