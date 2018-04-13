/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log.gui
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 18:42
 */
package cn.savor.standalone.log.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class DialogUI {

    public static void alert(Frame owner, String title, String content) {
        final JDialog dialog = new JDialog(owner, title, true);
        dialog.setSize(250, 150);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);

        JTextArea messageTextArea = new JTextArea(content);
        messageTextArea.setForeground(new Color(20, 20, 20));
        messageTextArea.setDisabledTextColor(new Color(30, 30, 30));
        messageTextArea.setBackground(null);
        messageTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
        messageTextArea.setEnabled(false);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(messageTextArea, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        JButton okBtn = new JButton("确定");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 关闭对话框
                dialog.dispose();
            }
        });
        southPanel.add(okBtn);
        panel.add(southPanel, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }
}
