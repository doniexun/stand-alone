/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:15
 */
package cn.savor.standalone.log.util;

/**
 * <h1>常量</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月09日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class Constants {

    /**
     * 属性
     */
    public static class Properties {

        /**
         * 键
         */
        public static class Keys {
            public static final String CITY = "cn.savor.offline.log.map.city";
            public static final String OSS_BUCKET = "cn.savor.offline.log.map.oss.bucket";
            public static final String OSS_OBJECT_KEY = "cn.savor.offline.log.map.oss.object.key";
        }
    }

    /**
     * 属性
     */
    public static class Configure {

        /**
         * 键
         */
        public static class Keys {
            public static final String CITY = "cn.savor.offline.log.configure.city";

            /**
             * URL
             */
            public static class Url {

                /**
                 * 区域
                 */
                public static class Area {
                    public static final String OFFLINE_V1 = "cn.savor.offline.log.configure.url.area.offlineV1";
                    public static final String OFFLINE_V3 = "cn.savor.offline.log.configure.url.area.offlineV3";
                }
            }

            /**
             * OSS
             */
            public static class OSS {
                public static final String BUCKET = "cn.savor.offline.log.configure.oss.bucket";

                /**
                 * Object-Key
                 */
                public static class ObjectKey {
                    public static final String OFFLINE_V1 = "cn.savor.offline.log.configure.oss.object.key.offlineV1";
                    public static final String OFFLINE_V3 = "cn.savor.offline.log.configure.oss.object.key.offlineV3";
                    public static final String MEDIA = "cn.savor.offline.log.configure.oss.object.key.media";
                }
            }

            /**
             * 目录
             */
            public static class Directory {
                /**
                 * 临时
                 */
                public static class Temp {
                    public static final String _ROOT = "cn.savor.offline.log.configure.directory.temp";

                    /**
                     * 上传
                     */
                    public static class Upload {
                        public static final String _ROOT = "cn.savor.offline.log.configure.directory.temp.upload";
                        public static final String OFFLINE_V1 = "cn.savor.offline.log.configure.directory.temp.upload.offlineV1";
                        public static final String OFFLINE_V3 = "cn.savor.offline.log.configure.directory.temp.upload.offlineV3";
                    }
                }

                /**
                 * 备份
                 */
                public static class Backup {
                    public static final String _ROOT = "cn.savor.offline.log.configure.directory.backup";

                    /**
                     * 上传
                     */
                    public static class Upload {
                        public static final String _ROOT = "cn.savor.offline.log.configure.directory.backup.upload";
                        public static final String OFFLINE_V1 = "cn.savor.offline.log.configure.directory.backup.upload.offlineV1";
                        public static final String OFFLINE_V3 = "cn.savor.offline.log.configure.directory.backup.upload.offlineV3";
                    }
                }

                /**
                 * 数据
                 */
                public static class Data {
                    public static final String _ROOT = "cn.savor.offline.log.configure.directory.data";
                    public static final String MEDIA = "cn.savor.offline.log.configure.directory.data.media";

                    /**
                     * 上传
                     */
                    public static class Upload {
                        public static final String _ROOT = "cn.savor.offline.log.configure.directory.data.upload";
                        public static final String OFFLINE_V1 = "cn.savor.offline.log.configure.directory.data.upload.offlineV1";
                        public static final String OFFLINE_V3 = "cn.savor.offline.log.configure.directory.data.upload.offlineV3";
                    }

                    /**
                     * 下载
                     */
                    public static class Download {
                        public static final String _ROOT = "cn.savor.offline.log.configure.directory.data.download";
                        public static final String OFFLINE_V1 = "cn.savor.offline.log.configure.directory.data.download.offlineV1";
                        public static final String OFFLINE_V3 = "cn.savor.offline.log.configure.directory.data.download.offlineV3";
                    }
                }
            }
        }

        /**
         * 目录
         */
        public static class Directory {
            public static final String USER_HOME = ".offline_logs";
            public static final String USER_WORK = "conf";
            public static final String DATA = "data";
            public static final String BACKUP = "backup";
            public static final String UPLOAD = "upload";
            public static final String STANDALONE_V1 = "standalone_v1";
            public static final String STANDALONE_V3 = "standalone_v3";
            public static final String MEDIA = "media";
        }
    }

    public static class UI {

    }

    /**
     * 服务器
     */
    public static class Server {
        /**
         * 状态
         */
        public static class Status {
            public static final int STARTING = 0;
            public static final int RUNNING = 1;
            public static final int STOPPING = 2;
            public static final int STOPPED = 3;
        }
    }
}
