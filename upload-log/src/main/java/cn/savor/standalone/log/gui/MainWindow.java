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

    private Context context;

    public void init() throws IOException, LoadException {
        ConfigureLoader configureLoader = new ConfigureLoader();
        Configure configure = configureLoader.loadUIData().loadConfig().getConfigure();
        context = new Context(configure);
        this.buildWindow();
    }

    /**
     * 构建窗口
     */
    private void buildWindow() {
        JFrame mainFrame = context.getTopWindow();

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setSize(mainFrame.getWidth(), mainFrame.getHeight());

        Component appConfigTabComponent = new AppConfigTabbedPane(context).buildUI(tabbedPane);
        Component appMainTabComponent = new AppMainTabbedPane(context).buildUI(tabbedPane);

        tabbedPane.setSelectedComponent(appMainTabComponent);
        mainFrame.add(tabbedPane);
        mainFrame.setVisible(true);
    }

    public void destroy() {
        System.out.println("window.destroy");
    }

    public static void main(String[] args) {
        try {
            MainWindow window = new MainWindow();
            window.init();
            window.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
