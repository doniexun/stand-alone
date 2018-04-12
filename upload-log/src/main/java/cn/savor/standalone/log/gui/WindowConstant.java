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

import cn.savor.standalone.log.Constants;

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
        static final String title = "日志操作工具";
        static final int width = 800;
        static final int height = 600;
    }

    static class AppTabbedPanel {
        static class Main {
            static final String title = "操作主页";

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
            static final String title = "系统配置";
            static final String icon = "/images/icon_system_config.png";
            static final String tip = "此系统的全局配置";

            static class City {
                static final String label = "城市";
                static final String fieldName = Constants.Configure.Keys.CITY;
            }

            static class OSSBucket {
                static final String label = "OSS 环境";
                static final String fieldName = Constants.Configure.Keys.OSS_BUCKET;
            }

            static class OSSObjectKey {
                static final String label = "OSS 前缀";
                static final String fieldName = Constants.Configure.Keys.OSS_OBJECT_KEY;
            }

            static class TempDirectory {
                static final String label = "临时目录";
                static final String fieldName = Constants.Configure.Keys.DIRECTORY_TEMP;
                static final String fileChooseButton = "...";
            }

            static class DataDirectory {
                static final String label = "数据目录";
                static final String fieldName = Constants.Configure.Keys.DIRECTORY_DATA;
                static final String fileChooseButton = "...";
            }

            static class SaveButton {
                static final String label = "保存";
                static final String tip = "把配置信息保存到电脑中。\r\n保存路径是 ： ";
            }
        }

        static class CopyFromUDisk {
            static final String title = "复制日志";
            static final String icon = "/images/icon_copy.png";
            static final String tip = "把日志文件从 U 盘复制到此电脑";

            static class ConfigurePanel {
                static final String title = "数据源";
            }

            static class OperationPanel {
                static final String title = "操作";
            }

            static class MessagePanel {
                static final String title = "信息";
            }
        }
    }

    public static class Page {
        public static class Key {
            public static class CopyFromUDisk {
                public static final String SOURCE = "cn.savor.offline.log.page.source";
            }
        }
    }
}
