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

import cn.savor.standalone.log.exception.LoadException;
import cn.savor.standalone.log.gui.bean.ApplicationContext;
import cn.savor.standalone.log.gui.page.*;
import cn.savor.standalone.log.gui.util.WindowConstant;
import cn.savor.standalone.log.model.Configure;
import cn.savor.standalone.log.util.ConfigureLoader;
import cn.savor.standalone.log.util.Constants;

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

    private ApplicationContext context;
    private AppConfigTabbedPanel appConfigTabbedPanel;
    private AppCopyTabbedPanel appCopyTabbedPanel;
    private AppMoveTabbedPanel appMoveTabbedPanel;
    private AppUploadTabbedPanel appUploadTabbedPanel;
    private AppDownloadTabbedPanel appDownloadTabbedPanel;

    void init() throws IOException, LoadException {
        ConfigureLoader configureLoader = new ConfigureLoader();
        Configure configure = configureLoader.loadUIData().loadConfig().getConfigure();
        context = new ApplicationContext(configure);
    }

    /**
     * 构建窗口
     */
    void run() {
        JFrame mainFrame = context.getTopWindow();

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setSize(mainFrame.getWidth(), mainFrame.getHeight());

        // 系统配置页面
        appConfigTabbedPanel = new AppConfigTabbedPanel(context);
        Component appConfigTabComponent = appConfigTabbedPanel.buildUI(tabbedPane);
//        Icon appConfigTabIcon = new ImageIcon(this.getClass().getResource(WindowConstant.AppTabbedPanel.Config.icon));
        tabbedPane.addTab(WindowConstant.AppTabbedPanel.Config.title, null, appConfigTabComponent, WindowConstant.AppTabbedPanel.Config.tip);
        tabbedPane.setEnabledAt(0, true);

        // 从 U 盘复制日志页面
        appCopyTabbedPanel = new AppCopyTabbedPanel(context);
        Component appCopyTabComponent = appCopyTabbedPanel.buildUI(tabbedPane);
//        Icon appCopyTabIcon = new ImageIcon(this.getClass().getResource(WindowConstant.AppTabbedPanel.CopyFromUDisk.icon));
        tabbedPane.addTab(WindowConstant.AppTabbedPanel.CopyFromUDisk.title, null, appCopyTabComponent, WindowConstant.AppTabbedPanel.CopyFromUDisk.tip);
        tabbedPane.setEnabledAt(1, true);

        // 从 U 盘移动日志页面
        appMoveTabbedPanel = new AppMoveTabbedPanel(context);
        Component appMoveTabComponent = appMoveTabbedPanel.buildUI(tabbedPane);
//        Icon appMoveTabIcon = new ImageIcon(this.getClass().getResource(WindowConstant.AppTabbedPanel.MoveFromUDisk.icon));
        tabbedPane.addTab(WindowConstant.AppTabbedPanel.MoveFromUDisk.title, null, appMoveTabComponent, WindowConstant.AppTabbedPanel.MoveFromUDisk.tip);
        tabbedPane.setEnabledAt(2, false);

        // 上传日志到 OSS 页面
        appUploadTabbedPanel = new AppUploadTabbedPanel(context);
        Component appUploadTabComponent = appUploadTabbedPanel.buildUI(tabbedPane);
//        Icon appUploadTabIcon = new ImageIcon(this.getClass().getResource(WindowConstant.AppTabbedPanel.UploadToOSS.icon));
        tabbedPane.addTab(WindowConstant.AppTabbedPanel.UploadToOSS.title, null, appUploadTabComponent, WindowConstant.AppTabbedPanel.UploadToOSS.tip);
        tabbedPane.setEnabledAt(3, true);

        // 到 OSS 下载日志页面
        appDownloadTabbedPanel = new AppDownloadTabbedPanel(context);
        Component appDownloadTabComponent = appUploadTabbedPanel.buildUI(tabbedPane);
//        Icon appDownloadTabIcon = new ImageIcon(this.getClass().getResource(WindowConstant.AppTabbedPanel.DownloadFromOSS.icon));
        tabbedPane.addTab(WindowConstant.AppTabbedPanel.DownloadFromOSS.title, null, appDownloadTabComponent, WindowConstant.AppTabbedPanel.DownloadFromOSS.tip);
        tabbedPane.setEnabledAt(3, true);

        tabbedPane.setSelectedComponent(appCopyTabComponent);
        mainFrame.setContentPane(tabbedPane);
        mainFrame.setVisible(true);

        while (context.getStatus() != Constants.Server.Status.STOPPED) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    void destroy() {
        appConfigTabbedPanel.close();
        appCopyTabbedPanel.close();
        appMoveTabbedPanel.close();
        appUploadTabbedPanel.close();
    }
}
