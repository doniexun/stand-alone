/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log.gui
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:37
 */
package cn.savor.standalone.log.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h1>用户界面 - 主入口</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class GuiMain {

    private static Logger logger = LoggerFactory.getLogger(GuiMain.class);

    public static void main(String[] args) {
        logger.info("Opening GUI ...");
        try {
            MainWindow window = new MainWindow();
            window.init();
            window.run();
            window.destroy();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("GUI is closed");
    }
}
