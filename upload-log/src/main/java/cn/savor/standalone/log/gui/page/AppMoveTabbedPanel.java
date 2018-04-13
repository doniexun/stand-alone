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
package cn.savor.standalone.log.gui.page;

import cn.savor.standalone.log.command.ICommand;
import cn.savor.standalone.log.command.local.file.move.CommandMove;
import cn.savor.standalone.log.gui.bean.ApplicationContext;
import cn.savor.standalone.log.gui.bean.PageContext;
import cn.savor.standalone.log.gui.io.JTextAreaAppendOutputStream;
import cn.savor.standalone.log.gui.io.JTextAreaSetTextOutputStream;
import cn.savor.standalone.log.gui.util.WindowConstant;
import cn.savor.standalone.log.util.Constants;
import net.lizhaoweb.common.util.argument.ArgumentFactory;
import net.lizhaoweb.common.util.base.Constant;
import net.lizhaoweb.common.util.base.FileUtil;
import net.lizhaoweb.common.util.base.IOUtil;
import net.lizhaoweb.common.util.base.StringUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>应用页面 - 从 U 盘移动</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月05日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class AppMoveTabbedPanel extends AppAbstractTabbedPanel {
    private static final int THIS_STATUS_INIT = -1;
    private static final int THIS_STATUS_SCAN_START = 0x00;
    private static final int THIS_STATUS_SCAN_END = 0x01;
    private static final int THIS_STATUS_OFFLINE_V1_START = 0x10;
    private static final int THIS_STATUS_OFFLINE_V1_END = 0x11;
    private static final int THIS_STATUS_OFFLINE_V3_START = 0x30;
    private static final int THIS_STATUS_OFFLINE_V3_END = 0x31;

    private int runningStatus;

    public AppMoveTabbedPanel(ApplicationContext context) {
        this.context = new PageContext(context);
        runningStatus = THIS_STATUS_INIT;
    }

    @Override
    void createConfigurePanel(JComponent parentPanel) {
        TitledBorder configurePanelTitledBorder = new TitledBorder(WindowConstant.AppTabbedPanel.MoveFromUDisk.ConfigurePanel.title);
        JPanel configurePanel = new JPanel();
        configurePanel.setPreferredSize(new Dimension(parentPanel.getWidth(), parentPanel.getHeight() * 7 / 15 - 120));
        configurePanel.setSize(parentPanel.getWidth(), parentPanel.getHeight() * 7 / 15 - 120);
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

        //安装一个流，将该流定向到output中
        sourceOutputStream = new JTextAreaSetTextOutputStream(textArea);
        scrollPane.setViewportView(textArea);
        configurePanel.add(scrollPane);
        parentPanel.add(configurePanel, BorderLayout.NORTH);
    }

    @Override
    void createOperationPanel(JComponent parentPanel) {
        TitledBorder configurePanelTitledBorder = new TitledBorder(WindowConstant.AppTabbedPanel.MoveFromUDisk.OperationPanel.title);
        JPanel configurePanel = new JPanel();
        configurePanel.setPreferredSize(new Dimension(parentPanel.getWidth(), 120));
        configurePanel.setBorder(configurePanelTitledBorder);

        JButton scanButton = new JButton("扫描");
        scanButton.setToolTipText("扫描 U 盘");
        scanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    checkRunningStatus();
                    runningStatus = THIS_STATUS_SCAN_START;
                    messagePrintln("开始扫描 U 盘 ... ");
                    context.removeParameters(WindowConstant.Page.Key.MoveFromUDisk.SOURCE);
                    File[] usbDiskRoots = FileUtil.listRootsForWindows(Constant.DriveType.Windows.REMOVABLE);
                    if (usbDiskRoots == null || usbDiskRoots.length < 1) {
                        messagePrintln(1, "无法读取 U 盘列表");
                    } else {
                        for (File usbDiskRoot : usbDiskRoots) {
                            File logDirectory = new File(usbDiskRoot, "log");
                            if (!logDirectory.exists()) {
                                messagePrintln(1, "%s 盘没有 'log' 目录", usbDiskRoot);
                                continue;
                            }
                            if (!logDirectory.isDirectory()) {
                                messagePrintln(1, "%s 盘下的 'log'，不是目录", usbDiskRoot);
                                continue;
                            }
                            if (!logDirectory.canExecute()) {
                                messagePrintln(1, "%s 盘下的 log 目录，不能执行", usbDiskRoot);
                                continue;
                            }
                            if (!logDirectory.canRead()) {
                                messagePrintln(1, "%s 盘下的 log 目录，不能读取", usbDiskRoot);
                                continue;
                            }
                            String logDirectoryPath = FileUtil.getCanonicalPath(logDirectory);
                            context.addParameter(WindowConstant.Page.Key.MoveFromUDisk.SOURCE, logDirectoryPath);
                        }
                    }
                    String[] pathArray = context.getParameters(WindowConstant.Page.Key.MoveFromUDisk.SOURCE);
                    if (pathArray == null) {
                        sourcePrintln("无 U 盘插入");
                    } else {
                        String textAreaContent = StringUtil.join(pathArray, "\n");
                        sourcePrintln(textAreaContent);
                    }
                    messagePrintln("扫描 U 盘完成");
                } catch (IOException e) {
                    messagePrintlnError(e);
                }
                messageNewLine();
                messageNewLine();
                runningStatus = THIS_STATUS_SCAN_END;
            }
        });
        configurePanel.add(scanButton);

        JButton moveOfflineV1Button = new JButton("移动一代");
        moveOfflineV1Button.setToolTipText("从 U 盘中把一代单机版日志复现到此台电脑上");
        moveOfflineV1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    checkRunningStatus();
                    runningStatus = THIS_STATUS_OFFLINE_V1_START;
                    messagePrintln("准备移动一代单机版日志 ... ");

                    List<String> argList = new ArrayList<>();
                    String[] sourcePathArray = context.getParameters(WindowConstant.Page.Key.MoveFromUDisk.SOURCE);
                    if (sourcePathArray == null) {
                        throw new IllegalArgumentException("移动一代单机版日志时，没有源目录");
                    }
                    for (String sourcePath : sourcePathArray) {
                        argList.add("srcDir=" + sourcePath);
                    }

                    String targetDirectoryPath = context.getConfig(Constants.Configure.Keys.Directory.Data.Upload.OFFLINE_V1);
                    if (StringUtil.isBlank(targetDirectoryPath)) {
                        throw new IllegalArgumentException("移动一代单机版日志时，没有目标目录");
                    }
                    argList.add("tarDir=" + targetDirectoryPath);
                    argList.add("regex=^\\d+_\\d*_\\d*\\.csv(:?\\.gz)?$");

                    ICommand command = new CommandMove(messageOutputStream);
                    ArgumentFactory.analysisArgument(argList.toArray(new String[0]));

                    messagePrintln(1, "开始移动： ");
                    command.execute();
                    messagePrintln("一代单机版日志移动完成");
                } catch (Exception e) {
                    messagePrintlnError(e);
                }
                messageNewLine();
                messageNewLine();
                runningStatus = THIS_STATUS_OFFLINE_V1_END;
            }
        });
        configurePanel.add(moveOfflineV1Button);

        JButton moveOfflineV3Button = new JButton("移动三代");
        moveOfflineV3Button.setToolTipText("从 U 盘中把三代单机版日志复现到此台电脑上");
        moveOfflineV3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    checkRunningStatus();
                    runningStatus = THIS_STATUS_OFFLINE_V3_START;
                    messagePrintln("准备移动三代单机版日志 ... ");

                    List<String> argList = new ArrayList<>();
                    String[] sourcePathArray = context.getParameters(WindowConstant.Page.Key.MoveFromUDisk.SOURCE);
                    if (sourcePathArray == null) {
                        throw new IllegalArgumentException("移动三代单机版日志时，没有源目录");
                    }
                    for (String sourcePath : sourcePathArray) {
                        argList.add("srcDir=" + sourcePath);
                    }

                    String targetDirectoryPath = context.getConfig(Constants.Configure.Keys.Directory.Data.Upload.OFFLINE_V3);
                    if (StringUtil.isBlank(targetDirectoryPath)) {
                        throw new IllegalArgumentException("移动三代单机版日志时，没有目标目录");
                    }
                    argList.add("tarDir=" + targetDirectoryPath);
                    argList.add("regex=^([0-9A-Fa-f]{12})_\\d*_standalone\\.blog\\.zip$");

                    ICommand command = new CommandMove(messageOutputStream);
                    ArgumentFactory.analysisArgument(argList.toArray(new String[0]));

                    messagePrintln(1, "开始移动： ");
                    command.execute();
                    messagePrintln("三代单机版日志移动完成");
                } catch (Exception e) {
                    messagePrintlnError(e);
                }
                messageNewLine();
                messageNewLine();
                runningStatus = THIS_STATUS_OFFLINE_V3_END;
            }
        });
        configurePanel.add(moveOfflineV3Button);

        parentPanel.add(configurePanel, BorderLayout.CENTER);
    }

    @Override
    void createMessagePanel(JComponent parentPanel) {
        TitledBorder configurePanelTitledBorder = new TitledBorder(WindowConstant.AppTabbedPanel.MoveFromUDisk.MessagePanel.title);
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

        //安装一个流，将该流定向到output中
        messageOutputStream = new JTextAreaAppendOutputStream(textArea);
        scrollPane.setViewportView(textArea);
        configurePanel.add(scrollPane);
        parentPanel.add(configurePanel, BorderLayout.SOUTH);
    }

    @Override
    public void close() {
        IOUtil.closeQuietly(sourceOutputStream, messageOutputStream);
    }

    private void checkRunningStatus() throws IOException {
        if (runningStatus == THIS_STATUS_SCAN_START) {
            this.messagePrintln("U 盘扫描没有结束");
        }
        if (runningStatus == THIS_STATUS_OFFLINE_V1_START) {
            this.messagePrintln("移动一代单机版日志没有结束");
        }
        if (runningStatus == THIS_STATUS_OFFLINE_V3_START) {
            this.messagePrintln("移动三代单机版日志没有结束");
        }
    }
}
