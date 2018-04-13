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
import cn.savor.standalone.log.command.local.file.copy.CommandCopy;
import cn.savor.standalone.log.gui.bean.ApplicationContext;
import cn.savor.standalone.log.gui.bean.PageContext;
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
public class AppCopyTabbedPanel extends AppAbstractTabbedPanel {
    private static final int THIS_STATUS_INIT = -1;
    private static final int THIS_STATUS_SCAN_START = 0x00;
    private static final int THIS_STATUS_SCAN_END = 0x01;
    private static final int THIS_STATUS_OFFLINE_V1_START = 0x10;
    private static final int THIS_STATUS_OFFLINE_V1_END = 0x11;
    private static final int THIS_STATUS_OFFLINE_V3_START = 0x30;
    private static final int THIS_STATUS_OFFLINE_V3_END = 0x31;

    private int runningStatus;

    public AppCopyTabbedPanel(ApplicationContext context) {
        this.context = new PageContext(context);
        runningStatus = THIS_STATUS_INIT;
    }

    @Override
    void createConfigurePanel(JComponent parentPanel) {
        TitledBorder configurePanelTitledBorder = new TitledBorder(WindowConstant.AppTabbedPanel.CopyFromUDisk.ConfigurePanel.title);
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
        sourceOutputStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }

            @Override
            public void write(byte[] bytes, int offset, int length) throws IOException {
                synchronized (this) {
                    textArea.setText(new String(bytes, offset, length));
                }
            }
        };

        scrollPane.setViewportView(textArea);
        configurePanel.add(scrollPane);
        parentPanel.add(configurePanel, BorderLayout.NORTH);
    }

    @Override
    void createOperationPanel(JComponent parentPanel) {
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
                    checkRunningStatus();
                    runningStatus = THIS_STATUS_SCAN_START;
                    context.removeParameters(WindowConstant.Page.Key.CopyFromUDisk.SOURCE);
                    File[] usbDiskRoots = FileUtil.listRootsForWindows(Constant.DriveType.Windows.REMOVABLE);
                    for (File usbDiskRoot : usbDiskRoots) {
                        File sourceFile = new File(usbDiskRoot, "log");
                        String sourceFilePath = FileUtil.getCanonicalPath(sourceFile);
                        context.addParameter(WindowConstant.Page.Key.CopyFromUDisk.SOURCE, sourceFilePath);
                    }
                    String[] pathArray = context.getParameters(WindowConstant.Page.Key.CopyFromUDisk.SOURCE);
                    if (pathArray == null) {
                        sourcePrintln("无 U 盘插入");
                    } else {
                        String textAreaContent = StringUtil.join(pathArray, "\n");
                        sourcePrintln(textAreaContent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runningStatus = THIS_STATUS_SCAN_END;
            }
        });
        configurePanel.add(scanButton);

        JButton copyOfflineV1Button = new JButton("复制一代");
        copyOfflineV1Button.setToolTipText("从 U 盘中把一代单机版日志复现到此台电脑上");
        copyOfflineV1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    checkRunningStatus();
                    runningStatus = THIS_STATUS_OFFLINE_V1_START;
                    messagePrintln("\n准备复制一代单机版日志 ... ");

                    List<String> argList = new ArrayList<>();
                    String[] sourcePathArray = context.getParameters(WindowConstant.Page.Key.CopyFromUDisk.SOURCE);
                    if (sourcePathArray == null) {
                        String errorMessage = "复制一代单机版日志时，没有源目录";
                        throw new IllegalArgumentException(errorMessage);
                    }
                    for (String sourcePath : sourcePathArray) {
                        argList.add("srcDir=" + sourcePath);
                    }

                    String targetDirectoryPath = context.getConfig(Constants.Configure.Keys.DIRECTORY_DATA_UPLOAD_OFFLINE_V1);
                    if (StringUtil.isBlank(targetDirectoryPath)) {
                        String errorMessage = "复制一代单机版日志时，没有目标目录";
                        throw new IllegalArgumentException(errorMessage);
                    }
                    argList.add("tarDir=" + targetDirectoryPath);
                    argList.add("regex=^\\d+_\\d*_\\d*\\.csv(:?\\.gz)?$");

                    ICommand command = new CommandCopy(messageOutputStream);
                    ArgumentFactory.analysisArgument(argList.toArray(new String[0]));

                    messagePrintln("\t开始复制： ");
                    command.execute();
                    messagePrintln("\n一代单机版日志复制完成\n");
                } catch (Exception e) {
                    messagePrintlnError(e);
                }
                runningStatus = THIS_STATUS_OFFLINE_V1_END;
            }
        });
        configurePanel.add(copyOfflineV1Button);

        JButton copyOfflineV3Button = new JButton("复制三代");
        copyOfflineV3Button.setToolTipText("从 U 盘中把三代单机版日志复现到此台电脑上");
        copyOfflineV3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    checkRunningStatus();
                    runningStatus = THIS_STATUS_OFFLINE_V3_START;
                    messagePrintln("\n准备复制三代单机版日志 ... ");

                    List<String> argList = new ArrayList<>();
                    String[] sourcePathArray = context.getParameters(WindowConstant.Page.Key.CopyFromUDisk.SOURCE);
                    if (sourcePathArray == null) {
                        String errorMessage = "复制三代单机版日志时，没有源目录";
                        throw new IllegalArgumentException(errorMessage);
                    }
                    for (String sourcePath : sourcePathArray) {
                        argList.add("srcDir=" + sourcePath);
                    }

                    String targetDirectoryPath = context.getConfig(Constants.Configure.Keys.DIRECTORY_DATA_UPLOAD_OFFLINE_V3);
                    if (StringUtil.isBlank(targetDirectoryPath)) {
                        String errorMessage = "复制三代单机版日志时，没有目标目录";
                        throw new IllegalArgumentException(errorMessage);
                    }
                    argList.add("tarDir=" + targetDirectoryPath);
                    argList.add("regex=^([0-9A-Fa-f]{12})_\\d*_standalone\\.blog\\.zip$");

                    ICommand command = new CommandCopy(messageOutputStream);
                    ArgumentFactory.analysisArgument(argList.toArray(new String[0]));

                    messagePrintln("\t开始复制： ");
                    command.execute();
                    messagePrintln("\n三代单机版日志复制完成\n");
                } catch (Exception e) {
                    messagePrintlnError(e);
                }
                runningStatus = THIS_STATUS_OFFLINE_V3_END;
            }
        });
        configurePanel.add(copyOfflineV3Button);

        parentPanel.add(configurePanel, BorderLayout.CENTER);
    }

    @Override
    void createMessagePanel(JComponent parentPanel) {
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
                synchronized (this) {
                    textArea.append(new String(bytes, offset, length));
                }
            }
        };

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
            this.messagePrintln("复制一代单机版日志没有结束");
        }
        if (runningStatus == THIS_STATUS_OFFLINE_V3_START) {
            this.messagePrintln("复制三代单机版日志没有结束");
        }
    }
}
