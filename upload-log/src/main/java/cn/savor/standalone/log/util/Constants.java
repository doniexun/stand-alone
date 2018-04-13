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
            public static final String OSS_BUCKET = "cn.savor.offline.log.configure.oss.bucket";
            public static final String OSS_OBJECT_KEY = "cn.savor.offline.log.configure.oss.object.key";
            public static final String DIRECTORY_TEMP = "cn.savor.offline.log.configure.directory.temp";
            public static final String DIRECTORY_DATA = "cn.savor.offline.log.configure.directory.data";
            public static final String DIRECTORY_DATA_UPLOAD = "cn.savor.offline.log.configure.directory.data.upload";
            public static final String DIRECTORY_DATA_UPLOAD_OFFLINE_V1 = "cn.savor.offline.log.configure.directory.data.upload.offlineV1";
            public static final String DIRECTORY_DATA_UPLOAD_OFFLINE_V3 = "cn.savor.offline.log.configure.directory.data.upload.offlineV3";
            public static final String DIRECTORY_DATA_MEDIA = "cn.savor.offline.log.configure.directory.data.media";
        }

        /**
         * 目录
         */
        public static class Directory {
            public static final String USER_HOME = ".offline_logs";
            public static final String USER_WORK = "conf";
            public static final String DATA = "data";
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
