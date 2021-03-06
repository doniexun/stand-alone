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
import cn.savor.standalone.log.command.oss.upload.CommandUpload;
import cn.savor.standalone.log.gui.bean.ApplicationContext;
import cn.savor.standalone.log.gui.bean.PageContext;
import cn.savor.standalone.log.gui.io.JTextAreaAppendOutputStream;
import cn.savor.standalone.log.gui.io.JTextAreaSetTextOutputStream;
import cn.savor.standalone.log.gui.util.WindowConstant;
import cn.savor.standalone.log.util.Constants;
import net.lizhaoweb.common.util.argument.ArgumentFactory;
import net.lizhaoweb.common.util.base.HttpUtil;
import net.lizhaoweb.common.util.base.IOUtil;
import net.lizhaoweb.common.util.base.OSUtil;
import net.lizhaoweb.common.util.base.StringUtil;
import net.lizhaoweb.common.util.base.date.DateConstant;
import net.lizhaoweb.common.util.base.date.DateUtil;

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
 * <h1>应用页面 - 上传到 OSS</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月05日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class AppUploadTabbedPanel extends AppAbstractTabbedPanel {
    private static final int THIS_STATUS_INIT = -1;
    private static final int THIS_STATUS_OFFLINE_V1_START = 0x10;
    private static final int THIS_STATUS_OFFLINE_V1_END = 0x11;
    private static final int THIS_STATUS_OFFLINE_V3_START = 0x30;
    private static final int THIS_STATUS_OFFLINE_V3_END = 0x31;

    private int runningStatus;

    public AppUploadTabbedPanel(ApplicationContext context) {
        this.context = new PageContext(context);
        runningStatus = THIS_STATUS_INIT;
    }

    @Override
    void createConfigurePanel(JComponent parentPanel) {
        TitledBorder configurePanelTitledBorder = new TitledBorder(WindowConstant.AppTabbedPanel.UploadToOSS.ConfigurePanel.title);
        JPanel configurePanel = new JPanel();
        configurePanel.setPreferredSize(new Dimension(parentPanel.getWidth(), parentPanel.getHeight() * 7 / 15 - 120));
        configurePanel.setSize(parentPanel.getWidth(), parentPanel.getHeight() * 7 / 15 - 120);
        configurePanel.setBorder(configurePanelTitledBorder);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension((int) (configurePanel.getWidth() * 0.97), configurePanel.getHeight() - 30));
        scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        JTextArea textArea = new JTextArea();
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
        TitledBorder configurePanelTitledBorder = new TitledBorder(WindowConstant.AppTabbedPanel.UploadToOSS.OperationPanel.title);
        JPanel configurePanel = new JPanel();
        configurePanel.setPreferredSize(new Dimension(parentPanel.getWidth(), 120));
        configurePanel.setBorder(configurePanelTitledBorder);

        JButton uploadOfflineV1Button = new JButton("上传一代");
        uploadOfflineV1Button.setToolTipText("从此台电脑中把一代单机版日志上传到云");
        uploadOfflineV1Button.addActionListener(new UploadButtonForOfflineV1ActionListener());
        configurePanel.add(uploadOfflineV1Button);

        JButton uploadOfflineV3Button = new JButton("上传三代");
        uploadOfflineV3Button.setToolTipText("从此台电脑中把三代单机版日志上传到云");
        uploadOfflineV3Button.addActionListener(new UploadButtonForOfflineV3ActionListener());
        configurePanel.add(uploadOfflineV3Button);

        parentPanel.add(configurePanel, BorderLayout.CENTER);
    }

    @Override
    void createMessagePanel(JComponent parentPanel) {
        TitledBorder configurePanelTitledBorder = new TitledBorder(WindowConstant.AppTabbedPanel.UploadToOSS.MessagePanel.title);
        JPanel configurePanel = new JPanel();
        configurePanel.setPreferredSize(new Dimension(parentPanel.getWidth(), parentPanel.getHeight() * 8 / 15));
        configurePanel.setSize(parentPanel.getWidth(), parentPanel.getHeight() * 8 / 15);
        configurePanel.setBorder(configurePanelTitledBorder);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension((int) (configurePanel.getWidth() * 0.97), configurePanel.getHeight() - 30));
        scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        JTextArea textArea = new JTextArea();
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
            this.messagePrintln("上传一代单机版日志没有结束");
        }
        if (runningStatus == THIS_STATUS_OFFLINE_V3_START) {
            this.messagePrintln("上传三代单机版日志没有结束");
        }
    }


    // ================================================ 内部类 ================================================
    // 上传按键动作监听器 - 一代单机版
    private class UploadButtonForOfflineV1ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                checkRunningStatus();
                runningStatus = THIS_STATUS_OFFLINE_V1_START;
                messagePrintln("准备上传一代单机版日志 ... ");

                // 本机时间校准
                String timeSyncURL = context.getConfig(Constants.Configure.Keys.Url.TIME_SYNC);
                OSUtil.correctingOSTimeForWindows(timeSyncURL);

                List<String> argList = new ArrayList<>();
                String sourceDirectoryPath = context.getConfig(Constants.Configure.Keys.Directory.Data.Upload.OFFLINE_V1);
                if (StringUtil.isBlank(sourceDirectoryPath)) {
                    throw new IllegalArgumentException("上传一代单机版日志时，没有源目录");
                }
                argList.add("fromDir=" + sourceDirectoryPath);

                String tempDirectoryPath = context.getConfig(Constants.Configure.Keys.Directory.Temp.Upload.OFFLINE_V1);
                if (StringUtil.isBlank(tempDirectoryPath)) {
                    throw new IllegalArgumentException("上传一代单机版日志时，没有临时目录");
                }
                argList.add("tempDir=" + tempDirectoryPath);

                String backupDirectoryPath = context.getConfig(Constants.Configure.Keys.Directory.Backup.Upload.OFFLINE_V1);
                if (StringUtil.isBlank(backupDirectoryPath)) {
                    throw new IllegalArgumentException("上传一代单机版日志时，没有备份目录");
                }
                argList.add("backupDir=" + backupDirectoryPath);

                String bucketName = context.getConfig(Constants.Configure.Keys.OSS.BUCKET);
                if (StringUtil.isBlank(bucketName)) {
                    throw new IllegalArgumentException("上传一代单机版日志时，没有 OSS 桶");
                }
                argList.add("bucketName=" + bucketName);


                String areaCode = context.getConfig(Constants.Configure.Keys.CITY);

                String keyPrefix = context.getConfig(Constants.Configure.Keys.OSS.ObjectKey.OFFLINE_V1);
                if (StringUtil.isBlank(keyPrefix)) {
                    throw new IllegalArgumentException("上传一代单机版日志时，没有 OSS 键");
                }
                keyPrefix = String.format("%s/%s/%s", keyPrefix, areaCode, DateUtil.getSystemDateString(DateConstant.Format.Intact.DATE_3));
                argList.add("keyPrefix=" + HttpUtil.formatPath(keyPrefix));

                ICommand command = new CommandUpload(messageOutputStream);
                ArgumentFactory.analysisArgument(argList.toArray(new String[0]));

                messagePrintln(1, "开始上传： ");
                command.execute();
                messagePrintln("一代单机版日志上传完成");
            } catch (Exception e) {
                messagePrintlnError(e);
            }
            messageNewLine();
            messageNewLine();
            runningStatus = THIS_STATUS_OFFLINE_V1_END;
        }
    }

    // 上传按键动作监听器 - 三代单机版
    private class UploadButtonForOfflineV3ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                checkRunningStatus();
                runningStatus = THIS_STATUS_OFFLINE_V3_START;
                messagePrintln("准备上传三代单机版日志 ... ");

                // 本机时间校准
                String timeSyncURL = context.getConfig(Constants.Configure.Keys.Url.TIME_SYNC);
                OSUtil.correctingOSTimeForWindows(timeSyncURL);

                List<String> argList = new ArrayList<>();
                String sourceDirectoryPath = context.getConfig(Constants.Configure.Keys.Directory.Data.Upload.OFFLINE_V3);
                if (StringUtil.isBlank(sourceDirectoryPath)) {
                    throw new IllegalArgumentException("上传三代单机版日志时，没有源目录");
                }
                argList.add("fromDir=" + sourceDirectoryPath);

                String tempDirectoryPath = context.getConfig(Constants.Configure.Keys.Directory.Temp.Upload.OFFLINE_V3);
                if (StringUtil.isBlank(tempDirectoryPath)) {
                    throw new IllegalArgumentException("上传三代单机版日志时，没有临时目录");
                }
                argList.add("tempDir=" + tempDirectoryPath);

                String backupDirectoryPath = context.getConfig(Constants.Configure.Keys.Directory.Backup.Upload.OFFLINE_V3);
                if (StringUtil.isBlank(backupDirectoryPath)) {
                    throw new IllegalArgumentException("上传三代单机版日志时，没有备份目录");
                }
                argList.add("backupDir=" + backupDirectoryPath);

                String bucketName = context.getConfig(Constants.Configure.Keys.OSS.BUCKET);
                if (StringUtil.isBlank(bucketName)) {
                    throw new IllegalArgumentException("上传三代单机版日志时，没有 OSS 桶");
                }
                argList.add("bucketName=" + bucketName);

                String keyPrefix = context.getConfig(Constants.Configure.Keys.OSS.ObjectKey.OFFLINE_V3);
                if (StringUtil.isBlank(keyPrefix)) {
                    throw new IllegalArgumentException("上传三代单机版日志时，没有 OSS 键");
                }
                keyPrefix = String.format("%s/{}/%s", keyPrefix, DateUtil.getSystemDateString(DateConstant.Format.Intact.DATE_3));
                argList.add("keyPrefix=" + HttpUtil.formatPath(keyPrefix));

                String areaUrl = String.format("%s?area_no=%s", context.getConfig(Constants.Configure.Keys.Url.Area.OFFLINE_V3), context.getConfig(Constants.Configure.Keys.CITY));
                if (StringUtil.isBlank(areaUrl)) {
                    throw new IllegalArgumentException("上传三代单机版日志时，没有区域 url 地址");
                }
                argList.add("areaUrl=" + areaUrl);

                ICommand command = new CommandUpload(messageOutputStream);
                ArgumentFactory.analysisArgument(argList.toArray(new String[0]));

                messagePrintln(1, "开始上传： ");
                command.execute();
                messagePrintln("三代单机版日志上传完成");
            } catch (Exception e) {
                messagePrintlnError(e);
            }
            messageNewLine();
            messageNewLine();
            runningStatus = THIS_STATUS_OFFLINE_V3_END;
        }
    }
}
