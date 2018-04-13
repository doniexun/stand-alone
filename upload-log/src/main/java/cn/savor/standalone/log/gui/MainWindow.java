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

import cn.savor.standalone.log.Configure;
import cn.savor.standalone.log.ConfigureLoader;
import cn.savor.standalone.log.Constants;
import cn.savor.standalone.log.exception.LoadException;

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

    public void init() throws IOException, LoadException {
        ConfigureLoader configureLoader = new ConfigureLoader();
        Configure configure = configureLoader.loadUIData().loadConfig().getConfigure();
        context = new ApplicationContext(configure);
    }

    /**
     * 构建窗口
     */
    public void run() {
        JFrame mainFrame = context.getTopWindow();

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setSize(mainFrame.getWidth(), mainFrame.getHeight());

        // 系统配置页面
        appConfigTabbedPanel = new AppConfigTabbedPanel(context);
        Component appConfigTabComponent = appConfigTabbedPanel.buildUI(tabbedPane);
//        Icon appConfigTabIcon = new ImageIcon(this.getClass().getResource(WindowConstant.AppTabbedPanel.Config.icon));
        tabbedPane.addTab(WindowConstant.AppTabbedPanel.Config.title, null, appConfigTabComponent, WindowConstant.AppTabbedPanel.Config.tip);

        // 从 U 盘复制日志页面
        appCopyTabbedPanel = new AppCopyTabbedPanel(context);
        Component appCopyTabComponent = appCopyTabbedPanel.buildUI(tabbedPane);
//        Icon appCopyTabIcon = new ImageIcon(this.getClass().getResource(WindowConstant.AppTabbedPanel.CopyFromUDisk.icon));
        tabbedPane.addTab(WindowConstant.AppTabbedPanel.CopyFromUDisk.title, null, appCopyTabComponent, WindowConstant.AppTabbedPanel.CopyFromUDisk.tip);

        tabbedPane.setSelectedComponent(appCopyTabComponent);
        mainFrame.setContentPane(tabbedPane);
        mainFrame.setVisible(true);

        while (context.getStatus() != Constants.Server.Status.STOPPED) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public void destroy() {
        appConfigTabbedPanel.close();
        appCopyTabbedPanel.close();
    }

    public static void main(String[] args) {
        try {
            MainWindow window = new MainWindow();
            window.init();
            window.run();
            window.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
