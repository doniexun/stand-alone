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

import cn.savor.standalone.log.Constants;
import cn.savor.standalone.log.ICommand;
import cn.savor.standalone.log.file.copy.CommandCopy;
import net.lizhaoweb.common.util.argument.ArgumentFactory;
import net.lizhaoweb.common.util.base.Constant;
import net.lizhaoweb.common.util.base.FileUtil;
import net.lizhaoweb.common.util.base.IOUtil;
import net.lizhaoweb.common.util.base.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月05日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
class AppCopyTabbedPanel {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private PageContext context;
    private OutputStream sourceOutputStream;
    private OutputStream messageOutputStream;

    AppCopyTabbedPanel(ApplicationContext context) {
        this.context = new PageContext(context);
    }

    Component buildUI(Container parentContainer) {
        JPanel mainPanel = new JPanel();
        mainPanel.setSize(parentContainer.getWidth(), parentContainer.getHeight());
        mainPanel.setLayout(new BorderLayout());

        this.createConfigurePanel(mainPanel);
        this.createOperationPanel(mainPanel);
        this.createMessagePanel(mainPanel);

        return mainPanel;
    }

    void close() {
        IOUtil.closeQuietly(sourceOutputStream, messageOutputStream);
    }

    private void createConfigurePanel(JComponent parentPanel) {
        TitledBorder configurePanelTitledBorder = new TitledBorder(WindowConstant.AppTabbedPanel.CopyFromUDisk.ConfigurePanel.title);
        JPanel configurePanel = new JPanel();
        configurePanel.setPreferredSize(new Dimension(parentPanel.getWidth(), parentPanel.getHeight() * 7 / 15 - 120));
        configurePanel.setSize(parentPanel.getWidth(), parentPanel.getHeight() * 7 / 15 - 120);
        configurePanel.setBorder(configurePanelTitledBorder);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension((int) (configurePanel.getWidth() * 0.97), (int) (configurePanel.getHeight() - 30)));
        scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        final JTextArea textArea = new JTextArea();
        textArea.setForeground(new Color(20, 20, 20));
        textArea.setDisabledTextColor(new Color(30, 30, 30));
        textArea.setBackground(null);
        textArea.setBorder(new EmptyBorder(0, 0, 0, 0));
        textArea.setEnabled(false);

        //安装一个流，将该流定向到output中
        sourceOutputStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }

            @Override
            public void write(byte[] bytes, int offset, int length) throws IOException {
                textArea.setText(new String(bytes, offset, length));
            }
        };

        scrollPane.setViewportView(textArea);
        configurePanel.add(scrollPane);
        parentPanel.add(configurePanel, BorderLayout.NORTH);
    }

    private void createOperationPanel(JComponent parentPanel) {
        TitledBorder configurePanelTitledBorder = new TitledBorder(WindowConstant.AppTabbedPanel.CopyFromUDisk.OperationPanel.title);
        JPanel configurePanel = new JPanel();
        configurePanel.setPreferredSize(new Dimension(parentPanel.getWidth(), 120));
        configurePanel.setBorder(configurePanelTitledBorder);

        JButton scanButton = new JButton("扫描");
        scanButton.setToolTipText("扫描 U 盘");
        scanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    context.removeParameters(WindowConstant.Page.Key.CopyFromUDisk.SOURCE);
                    File[] usbDiskRoots = FileUtil.listRootsForWindows(Constant.DriveType.Windows.REMOVABLE);
                    for (File usbDiskRoot : usbDiskRoots) {
                        File sourceFile = new File(usbDiskRoot, "log");
                        String sourceFilePath = FileUtil.getCanonicalPath(sourceFile);
                        context.addParameter(WindowConstant.Page.Key.CopyFromUDisk.SOURCE, sourceFilePath);
                    }
                    String textAreaContent = StringUtil.join(context.getParameters(WindowConstant.Page.Key.CopyFromUDisk.SOURCE), "\n");
                    sourceOutputStream.write(textAreaContent.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        configurePanel.add(scanButton);

        JButton copyOfflineV1Button = new JButton("复制一代");
        copyOfflineV1Button.setToolTipText("从 U 盘中把日志复现到此台电脑上");
        copyOfflineV1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    List<String> argList = new ArrayList<>();
                    String[] sourcePathArray = context.getParameters(WindowConstant.Page.Key.CopyFromUDisk.SOURCE);
                    for (String sourcePath : sourcePathArray) {
                        argList.add("srcDir=" + sourcePath);
                    }
                    String targetDirectoryPath = context.getApplicationContext().getConfig(Constants.Configure.Keys.DIRECTORY_DATA_UPLOAD_OFFLINE_V1);
                    argList.add("tarDir=" + targetDirectoryPath);

                    ICommand command = new CommandCopy(messageOutputStream);
                    ArgumentFactory.analysisArgument(argList.toArray(new String[0]));
                    command.execute();
                } catch (Exception e) {
                    printError(e);
                }
            }
        });
        configurePanel.add(copyOfflineV1Button);

        JButton copyOfflineV3Button = new JButton("复制三代");
        copyOfflineV3Button.setToolTipText("从 U 盘中把日志复现到此台电脑上");
        copyOfflineV3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    List<String> argList = new ArrayList<>();
                    String[] sourcePathArray = context.getParameters(WindowConstant.Page.Key.CopyFromUDisk.SOURCE);
                    for (String sourcePath : sourcePathArray) {
                        argList.add("srcDir=" + sourcePath);
                    }
                    String targetDirectoryPath = context.getApplicationContext().getConfig(Constants.Configure.Keys.DIRECTORY_DATA_UPLOAD_OFFLINE_V3);
                    argList.add("tarDir=" + targetDirectoryPath);

                    ICommand command = new CommandCopy(messageOutputStream);
                    ArgumentFactory.analysisArgument(argList.toArray(new String[0]));
                    command.execute();
                } catch (Exception e) {
                    printError(e);
                }
            }
        });
        configurePanel.add(copyOfflineV3Button);

        parentPanel.add(configurePanel, BorderLayout.CENTER);
    }

    private void printError(Exception e) {
        try {
            logger.error(e.getMessage(), e);
            String errorString = String.format("Error : %s\n", e.getMessage());
            messageOutputStream.write(errorString.getBytes());
        } catch (IOException e1) {
            logger.error(e1.getMessage(), e1);
        }
    }

    private void createMessagePanel(JComponent parentPanel) {
        TitledBorder configurePanelTitledBorder = new TitledBorder(WindowConstant.AppTabbedPanel.CopyFromUDisk.MessagePanel.title);
        JPanel configurePanel = new JPanel();
        configurePanel.setPreferredSize(new Dimension(parentPanel.getWidth(), parentPanel.getHeight() * 8 / 15));
        configurePanel.setSize(parentPanel.getWidth(), parentPanel.getHeight() * 8 / 15);
        configurePanel.setBorder(configurePanelTitledBorder);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension((int) (configurePanel.getWidth() * 0.97), configurePanel.getHeight() - 30));
        scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        final JTextArea textArea = new JTextArea();
        textArea.setForeground(new Color(20, 20, 20));
        textArea.setDisabledTextColor(new Color(30, 30, 30));
        textArea.setBackground(null);
        textArea.setBorder(new EmptyBorder(0, 0, 0, 0));
        textArea.setEnabled(false);

        scrollPane.setViewportView(textArea);
        configurePanel.add(scrollPane);

        //安装一个流，将该流定向到output中
        messageOutputStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }

            @Override
            public void write(byte[] bytes, int offset, int length) throws IOException {
                textArea.append(new String(bytes, offset, length));
            }
        };

        scrollPane.setViewportView(textArea);
        configurePanel.add(scrollPane);
        parentPanel.add(configurePanel, BorderLayout.SOUTH);
    }
}
