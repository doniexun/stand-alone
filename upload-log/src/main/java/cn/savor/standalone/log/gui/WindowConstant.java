/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log.gui
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 18:13
 */
package cn.savor.standalone.log.gui;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月04日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
class WindowConstant {

    static class MainFrame {
        static final String icon = "/images/icon.png";
        static final String title = "例子";
        static final int width = 800;
        static final int height = 600;
    }

    static class AppTabbedPane {
        static class Main {
            static final String title = "主页";

            static class ConfigurePanel {
                static final String title = "配置";
            }

            static class OperationPanel {
                static final String title = "操作";
            }

            static class MessagePanel {
                static final String title = "信息";
            }
        }

        static class Config {
            static final String title = "配置";
        }
    }
}
