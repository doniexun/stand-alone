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
import cn.savor.standalone.log.command.oss.download.CommandDownload;
import cn.savor.standalone.log.gui.bean.ApplicationContext;
import cn.savor.standalone.log.gui.bean.PageContext;
import cn.savor.standalone.log.gui.io.JTextAreaAppendOutputStream;
import cn.savor.standalone.log.gui.io.JTextAreaSetTextOutputStream;
import cn.savor.standalone.log.gui.util.WindowConstant;
import cn.savor.standalone.log.util.Constants;
import net.lizhaoweb.common.util.argument.ArgumentFactory;
import net.lizhaoweb.common.util.base.IOUtil;
import net.lizhaoweb.common.util.base.OSUtil;
import net.lizhaoweb.common.util.base.StringUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>应用页面 - 从 OSS 下载</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月05日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class AppDownloadTabbedPanel extends AppAbstractTabbedPanel {
    private static final int THIS_STATUS_INIT = -1;
    private static final int THIS_STATUS_OFFLINE_V1_START = 0x10;
    private static final int THIS_STATUS_OFFLINE_V1_END = 0x11;
    private static final int THIS_STATUS_OFFLINE_V3_START = 0x30;
    private static final int THIS_STATUS_OFFLINE_V3_END = 0x31;

    private int runningStatus;

    public AppDownloadTabbedPanel(ApplicationContext context) {
        this.context = new PageContext(context);
        runningStatus = THIS_STATUS_INIT;
    }

    @Override
    void createConfigurePanel(JComponent parentPanel) {
        TitledBorder configurePanelTitledBorder = new TitledBorder(WindowConstant.AppTabbedPanel.DownloadFromOSS.ConfigurePanel.title);
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
        TitledBorder configurePanelTitledBorder = new TitledBorder(WindowConstant.AppTabbedPanel.DownloadFromOSS.OperationPanel.title);
        JPanel configurePanel = new JPanel();
        configurePanel.setPreferredSize(new Dimension(parentPanel.getWidth(), 120));
        configurePanel.setBorder(configurePanelTitledBorder);

        JButton downloadOfflineV1Button = new JButton("下载一代");
        downloadOfflineV1Button.setToolTipText("从云端把一代单机版日志下载到此台电脑上");
        downloadOfflineV1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    checkRunningStatus();
                    runningStatus = THIS_STATUS_OFFLINE_V1_START;
                    messagePrintln("准备下载一代单机版日志 ... ");

                    //TODO 本机时间校验
                    String timeSyncURL = context.getConfig(Constants.Configure.Keys.Url.TIME_SYNC);
                    OSUtil.correctingOSTimeForWindows(timeSyncURL);

                    List<String> argList = new ArrayList<>();
                    String sourceDirectoryPath = context.getConfig(Constants.Configure.Keys.Directory.Data.Download.OFFLINE_V1);
                    if (StringUtil.isBlank(sourceDirectoryPath)) {
                        throw new IllegalArgumentException("下载一代单机版日志时，没有源目录");
                    }
                    argList.add("fromDir=" + sourceDirectoryPath);

                    String tempDirectoryPath = context.getConfig(Constants.Configure.Keys.Directory.Temp.Download.OFFLINE_V1);
                    if (StringUtil.isBlank(tempDirectoryPath)) {
                        throw new IllegalArgumentException("下载一代单机版日志时，没有临时目录");
                    }
                    argList.add("tempDir=" + tempDirectoryPath);

                    String backupDirectoryPath = context.getConfig(Constants.Configure.Keys.Directory.Backup.Download.OFFLINE_V1);
                    if (StringUtil.isBlank(backupDirectoryPath)) {
                        throw new IllegalArgumentException("下载一代单机版日志时，没有备份目录");
                    }
                    argList.add("backupDir=" + backupDirectoryPath);

                    String bucketName = context.getConfig(Constants.Configure.Keys.OSS.BUCKET);
                    if (StringUtil.isBlank(bucketName)) {
                        throw new IllegalArgumentException("下载一代单机版日志时，没有 OSS 桶");
                    }
                    argList.add("bucketName=" + bucketName);

                    String keyPrefix = context.getConfig(Constants.Configure.Keys.OSS.ObjectKey.OFFLINE_V1);
                    if (StringUtil.isBlank(keyPrefix)) {
                        throw new IllegalArgumentException("下载一代单机版日志时，没有 OSS 键");
                    }
                    argList.add("keyPrefix=" + keyPrefix);

//                    String areaUrl = String.format("%s?area_no=%s", context.getConfig(Constants.Configure.Keys.Url.Area.OFFLINE_V1), context.getConfig(Constants.Configure.Keys.CITY));
//                    if (StringUtil.isBlank(areaUrl)) {
//                        throw new IllegalArgumentException("下载一代单机版日志时，没有区域 url 地址");
//                    }
//                    argList.add("areaUrl=" + areaUrl);

                    ICommand command = new CommandDownload(messageOutputStream);
                    ArgumentFactory.analysisArgument(argList.toArray(new String[0]));

                    messagePrintln(1, "开始下载： ");
                    command.execute();
                    messagePrintln("一代单机版日志下载完成");
                } catch (Exception e) {
                    messagePrintlnError(e);
                }
                messageNewLine();
                messageNewLine();
                runningStatus = THIS_STATUS_OFFLINE_V1_END;
            }
        });
        configurePanel.add(downloadOfflineV1Button);

        JButton downloadOfflineV3Button = new JButton("下载三代");
        downloadOfflineV3Button.setToolTipText("从云端把三代单机版日志下载到此台电脑上");
        downloadOfflineV3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    checkRunningStatus();
                    runningStatus = THIS_STATUS_OFFLINE_V3_START;
                    messagePrintln("准备下载三代单机版日志 ... ");

                    //TODO 本机时间校验
                    String timeSyncURL = context.getConfig(Constants.Configure.Keys.Url.TIME_SYNC);
                    OSUtil.correctingOSTimeForWindows(timeSyncURL);

                    List<String> argList = new ArrayList<>();
                    String sourceDirectoryPath = context.getConfig(Constants.Configure.Keys.Directory.Data.Download.OFFLINE_V3);
                    if (StringUtil.isBlank(sourceDirectoryPath)) {
                        throw new IllegalArgumentException("下载三代单机版日志时，没有源目录");
                    }
                    argList.add("fromDir=" + sourceDirectoryPath);

                    String tempDirectoryPath = context.getConfig(Constants.Configure.Keys.Directory.Temp.Download.OFFLINE_V3);
                    if (StringUtil.isBlank(tempDirectoryPath)) {
                        throw new IllegalArgumentException("下载三代单机版日志时，没有临时目录");
                    }
                    argList.add("tempDir=" + tempDirectoryPath);

                    String backupDirectoryPath = context.getConfig(Constants.Configure.Keys.Directory.Backup.Download.OFFLINE_V3);
                    if (StringUtil.isBlank(backupDirectoryPath)) {
                        throw new IllegalArgumentException("下载三代单机版日志时，没有备份目录");
                    }
                    argList.add("backupDir=" + backupDirectoryPath);

                    String bucketName = context.getConfig(Constants.Configure.Keys.OSS.BUCKET);
                    if (StringUtil.isBlank(bucketName)) {
                        throw new IllegalArgumentException("下载三代单机版日志时，没有 OSS 桶");
                    }
                    argList.add("bucketName=" + bucketName);

                    String keyPrefix = context.getConfig(Constants.Configure.Keys.OSS.ObjectKey.OFFLINE_V3);
                    if (StringUtil.isBlank(keyPrefix)) {
                        throw new IllegalArgumentException("下载三代单机版日志时，没有 OSS 键");
                    }
                    argList.add("keyPrefix=" + keyPrefix);

                    String areaUrl = String.format("%s?area_no=%s", context.getConfig(Constants.Configure.Keys.Url.Area.OFFLINE_V3), context.getConfig(Constants.Configure.Keys.CITY));
                    if (StringUtil.isBlank(areaUrl)) {
                        throw new IllegalArgumentException("下载三代单机版日志时，没有区域 url 地址");
                    }
                    argList.add("areaUrl=" + areaUrl);

                    ICommand command = new CommandDownload(messageOutputStream);
                    ArgumentFactory.analysisArgument(argList.toArray(new String[0]));

                    messagePrintln(1, "开始下载： ");
                    command.execute();
                    messagePrintln("三代单机版日志下载完成");
                } catch (Exception e) {
                    messagePrintlnError(e);
                }
                messageNewLine();
                messageNewLine();
                runningStatus = THIS_STATUS_OFFLINE_V3_END;
            }
        });
        configurePanel.add(downloadOfflineV3Button);

        parentPanel.add(configurePanel, BorderLayout.CENTER);
    }

    @Override
    void createMessagePanel(JComponent parentPanel) {
        TitledBorder configurePanelTitledBorder = new TitledBorder(WindowConstant.AppTabbedPanel.DownloadFromOSS.MessagePanel.title);
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
        if (runningStatus == THIS_STATUS_OFFLINE_V1_START) {
            this.messagePrintln("下载一代单机版日志没有结束");
        }
        if (runningStatus == THIS_STATUS_OFFLINE_V3_START) {
            this.messagePrintln("下载三代单机版日志没有结束");
        }
    }
}
