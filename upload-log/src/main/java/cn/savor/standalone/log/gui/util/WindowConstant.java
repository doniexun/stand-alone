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
package cn.savor.standalone.log.gui.util;

import cn.savor.standalone.log.util.Constants;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月04日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class WindowConstant {

    public static class MainFrame {
        public static final String icon = "/images/icon.png";
        public static final String title = "日志操作工具";
        public static final int width = 800;
        public static final int height = 600;
    }

    public static class AppTabbedPanel {
        public static class Main {
            public static final String title = "操作主页";

            public static class ConfigurePanel {
                public static final String title = "配置";
            }

            public static class OperationPanel {
                public static final String title = "操作";
            }

            public static class MessagePanel {
                public static final String title = "信息";
            }
        }

        public static class Config {
            public static final String title = "系统配置";
            public static final String icon = "/images/icon_system_config.png";
            public static final String tip = "此系统的全局配置";

            public static class City {
                public static final String label = "城市";
                public static final String fieldName = Constants.Configure.Keys.CITY;
            }

            public static class OSSBucket {
                public static final String label = "OSS 环境";
                public static final String fieldName = Constants.Configure.Keys.OSS.BUCKET;
            }

            public static class OSSObjectKey {
                public static final String label = "OSS 前缀";
                public static final String fieldName = Constants.Configure.Keys.OSS.ObjectKey.OFFLINE_V1;
            }

            public static class TempDirectory {
                public static final String label = "临时目录";
                public static final String fieldName = Constants.Configure.Keys.Directory.Temp._ROOT;
                public static final String fileChooseButton = "...";
            }

            public static class DataDirectory {
                public static final String label = "数据目录";
                public static final String fieldName = Constants.Configure.Keys.Directory.Data._ROOT;
                public static final String fileChooseButton = "...";
            }

            public static class BackupDirectory {
                public static final String label = "备份目录";
                public static final String fieldName = Constants.Configure.Keys.Directory.Backup._ROOT;
                public static final String fileChooseButton = "...";
            }

            public static class SaveButton {
                public static final String label = "保存";
                public static final String tip = "把配置信息保存到电脑中。\r\n保存路径是 ： ";
            }
        }

        public static class CopyFromUDisk {
            public static final String title = "复制日志";
            public static final String icon = "/images/icon_copy.png";
            public static final String tip = "把日志文件从 U 盘复制到此电脑";

            public static class ConfigurePanel {
                public static final String title = "数据源";
            }

            public static class OperationPanel {
                public static final String title = "操作";
            }

            public static class MessagePanel {
                public static final String title = "信息";
            }
        }

        public static class MoveFromUDisk {
            public static final String title = "移动日志";
            public static final String icon = "/images/icon_move.png";
            public static final String tip = "把日志文件从 U 盘移动到此电脑";

            public static class ConfigurePanel {
                public static final String title = "数据源";
            }

            public static class OperationPanel {
                public static final String title = "操作";
            }

            public static class MessagePanel {
                public static final String title = "信息";
            }
        }

        public static class UploadToOSS {
            public static final String title = "上传到云";
            public static final String icon = "/images/icon_upload.png";
            public static final String tip = "把日志文件从此电脑上传到云端";

            public static class ConfigurePanel {
                public static final String title = "数据源";
            }

            public static class OperationPanel {
                public static final String title = "操作";
            }

            public static class MessagePanel {
                public static final String title = "信息";
            }
        }

        public static class DownloadFromOSS {
            public static final String title = "从云下载";
            public static final String icon = "/images/icon_download.png";
            public static final String tip = "把日志文件从云端下载到此电脑";

            public static class ConfigurePanel {
                public static final String title = "数据源";
            }

            public static class OperationPanel {
                public static final String title = "操作";
            }

            public static class MessagePanel {
                public static final String title = "信息";
            }
        }
    }

    public static class Page {
        public static class Key {
            public static class CopyFromUDisk {
                public static final String SOURCE = "cn.savor.offline.log.page.source";
            }

            public static class MoveFromUDisk {
                public static final String SOURCE = "cn.savor.offline.log.page.source";
            }

            public static class UploadToOSS {
                public static final String SOURCE = "cn.savor.offline.log.page.source";
            }
        }
    }
}
