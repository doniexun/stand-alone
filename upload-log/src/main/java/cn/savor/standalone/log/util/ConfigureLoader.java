/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:24
 */
package cn.savor.standalone.log.util;


import cn.savor.standalone.log.exception.LoadException;
import cn.savor.standalone.log.model.Configure;
import cn.savor.standalone.log.model.ItemKeyValue;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.lizhaoweb.common.util.base.FileUtil;
import net.lizhaoweb.common.util.base.JsonUtil;
import net.lizhaoweb.common.util.base.StringUtil;

import java.io.File;
import java.util.List;
import java.util.Properties;

/**
 * <h1>装载器 - 配置</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月09日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@RequiredArgsConstructor
public class ConfigureLoader {

    @NonNull
    @Getter
    private Configure configure;

    @NonNull
    private FileLoader fileLoader;

    public ConfigureLoader() {
        configure = new Configure();
        fileLoader = new FileLoader();
    }

    public ConfigureLoader loadUIData() throws LoadException {
        Properties properties = fileLoader.loadDataFile(configure);
        this.loadUIDataFromProperties(properties, Constants.Properties.Keys.CITY, configure);
        this.loadUIDataFromProperties(properties, Constants.Properties.Keys.OSS_BUCKET, configure);
        this.loadUIDataFromProperties(properties, Constants.Properties.Keys.OSS_OBJECT_KEY, configure);
        return this;
    }

    public ConfigureLoader loadConfig() throws LoadException {
        // 加载
        Properties properties = fileLoader.loadConfigFile(configure);
        this.loadConfigFromProperties(properties, Constants.Configure.Keys.Url.TIME_SYNC, configure);
        this.loadConfigFromProperties(properties, Constants.Configure.Keys.CITY, configure);
        this.loadConfigFromProperties(properties, Constants.Configure.Keys.OSS.BUCKET, configure);
        this.loadConfigFromProperties(properties, Constants.Configure.Keys.Directory.Temp._ROOT, configure);
        this.loadConfigFromProperties(properties, Constants.Configure.Keys.Directory.Data._ROOT, configure);
        this.loadConfigFromProperties(properties, Constants.Configure.Keys.Directory.Backup._ROOT, configure);

        this.loadConfigFromProperties(properties, Constants.Configure.Keys.OSS.ObjectKey.OFFLINE_V1, configure);
        this.loadConfigFromProperties(properties, Constants.Configure.Keys.Url.Area.OFFLINE_V1, configure);

        this.loadConfigFromProperties(properties, Constants.Configure.Keys.OSS.ObjectKey.OFFLINE_V3, configure);
        this.loadConfigFromProperties(properties, Constants.Configure.Keys.Url.Area.OFFLINE_V3, configure);

        this.loadConfigFromProperties(properties, Constants.Configure.Keys.OSS.ObjectKey.MEDIA, configure);


        // ============================= 校验 Start =============================
        this.loadCheckForTemp();
        this.loadCheckForData();
        this.loadCheckForBackup();
        // ============================= 校验 End =============================

        return this;
    }

    // 校验 Backup
    private void loadCheckForBackup() {
        // backup
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Backup._ROOT))) {
            File defaultDirectory = new File(configure.getWorkDirectoryPath(), Constants.Configure.Directory.BACKUP);
            configure.putConfig(Constants.Configure.Keys.Directory.Backup._ROOT, FileUtil.getCanonicalPath(defaultDirectory));
        }
        String defaultBackupDirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Backup._ROOT);

        // =========================================== upload ===========================================
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Backup.Upload._ROOT))) {
            File defaultDirectory = new File(defaultBackupDirectoryName, Constants.Configure.Directory.UPLOAD);
            configure.putConfig(Constants.Configure.Keys.Directory.Backup.Upload._ROOT, FileUtil.getCanonicalPath(defaultDirectory));
        }
        String defaultUploadDirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Backup.Upload._ROOT);

        // standalone_v1
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Backup.Upload.OFFLINE_V1))) {
            File defaultDirectory = new File(defaultUploadDirectoryName, Constants.Configure.Directory.STANDALONE_V1);
            configure.putConfig(Constants.Configure.Keys.Directory.Backup.Upload.OFFLINE_V1, FileUtil.getCanonicalPath(defaultDirectory));
        }
//        String defaultUploadOfflineV1DirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Backup.Upload.OFFLINE_V1);

        // standalone_v3
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Backup.Upload.OFFLINE_V3))) {
            File defaultDirectory = new File(defaultUploadDirectoryName, Constants.Configure.Directory.STANDALONE_V3);
            configure.putConfig(Constants.Configure.Keys.Directory.Backup.Upload.OFFLINE_V3, FileUtil.getCanonicalPath(defaultDirectory));
        }
//        String defaultUploadOfflineV3DirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Backup.Upload.OFFLINE_V3);

        // =========================================== download ===========================================
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Backup.Download._ROOT))) {
            File defaultDirectory = new File(defaultBackupDirectoryName, Constants.Configure.Directory.DOWNLOAD);
            configure.putConfig(Constants.Configure.Keys.Directory.Backup.Download._ROOT, FileUtil.getCanonicalPath(defaultDirectory));
        }
        String defaultDownloadDirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Backup.Download._ROOT);

        // standalone_v1
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Backup.Download.OFFLINE_V1))) {
            File defaultDirectory = new File(defaultDownloadDirectoryName, Constants.Configure.Directory.STANDALONE_V1);
            configure.putConfig(Constants.Configure.Keys.Directory.Backup.Download.OFFLINE_V1, FileUtil.getCanonicalPath(defaultDirectory));
        }
//        String defaultDownloadOfflineV1DirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Backup.Download.OFFLINE_V1);

        // standalone_v3
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Backup.Download.OFFLINE_V3))) {
            File defaultDirectory = new File(defaultDownloadDirectoryName, Constants.Configure.Directory.STANDALONE_V3);
            configure.putConfig(Constants.Configure.Keys.Directory.Backup.Download.OFFLINE_V3, FileUtil.getCanonicalPath(defaultDirectory));
        }
//        String defaultDownloadOfflineV3DirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Backup.Download.OFFLINE_V3);
    }

    // 校验 Data
    private void loadCheckForData() {
        // data
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Data._ROOT))) {
            File defaultDirectory = new File(configure.getWorkDirectoryPath(), Constants.Configure.Directory.DATA);
            configure.putConfig(Constants.Configure.Keys.Directory.Data._ROOT, FileUtil.getCanonicalPath(defaultDirectory));
        }
        String defaultDataDirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Data._ROOT);

        // =========================================== upload ===========================================
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Data.Upload._ROOT))) {
            File defaultDirectory = new File(defaultDataDirectoryName, Constants.Configure.Directory.UPLOAD);
            configure.putConfig(Constants.Configure.Keys.Directory.Data.Upload._ROOT, FileUtil.getCanonicalPath(defaultDirectory));
        }
        String defaultUploadDirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Data.Upload._ROOT);

        // standalone_v1
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Data.Upload.OFFLINE_V1))) {
            File defaultDirectory = new File(defaultUploadDirectoryName, Constants.Configure.Directory.STANDALONE_V1);
            configure.putConfig(Constants.Configure.Keys.Directory.Data.Upload.OFFLINE_V1, FileUtil.getCanonicalPath(defaultDirectory));
        }
//        String defaultOfflineV1DirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Data.Upload.OFFLINE_V1);

        // standalone_v3
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Data.Upload.OFFLINE_V3))) {
            File defaultDirectory = new File(defaultUploadDirectoryName, Constants.Configure.Directory.STANDALONE_V3);
            configure.putConfig(Constants.Configure.Keys.Directory.Data.Upload.OFFLINE_V3, FileUtil.getCanonicalPath(defaultDirectory));
        }
//        String defaultOfflineV3DirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Data.Upload.OFFLINE_V3);

        // =========================================== download ===========================================
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Data.Download._ROOT))) {
            File defaultDirectory = new File(defaultDataDirectoryName, Constants.Configure.Directory.DOWNLOAD);
            configure.putConfig(Constants.Configure.Keys.Directory.Data.Download._ROOT, FileUtil.getCanonicalPath(defaultDirectory));
        }
        String defaultDownloadDirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Data.Download._ROOT);

        // standalone_v1
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Data.Download.OFFLINE_V1))) {
            File defaultDirectory = new File(defaultDownloadDirectoryName, Constants.Configure.Directory.STANDALONE_V1);
            configure.putConfig(Constants.Configure.Keys.Directory.Data.Download.OFFLINE_V1, FileUtil.getCanonicalPath(defaultDirectory));
        }
//        String defaultDownloadOfflineV1DirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Data.Download.OFFLINE_V1);

        // standalone_v3
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Data.Download.OFFLINE_V3))) {
            File defaultDirectory = new File(defaultDownloadDirectoryName, Constants.Configure.Directory.STANDALONE_V3);
            configure.putConfig(Constants.Configure.Keys.Directory.Data.Download.OFFLINE_V3, FileUtil.getCanonicalPath(defaultDirectory));
        }
//        String defaultDownloadOfflineV3DirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Data.Download.OFFLINE_V3);

        // =========================================== media ===========================================
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Data.MEDIA))) {
            File defaultDirectory = new File(defaultDataDirectoryName, Constants.Configure.Directory.MEDIA);
            configure.putConfig(Constants.Configure.Keys.Directory.Data.MEDIA, FileUtil.getCanonicalPath(defaultDirectory));
        }
//        String defaultMediaDirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Data.MEDIA);
    }

    // 校验 Temp
    private void loadCheckForTemp() {
        // temp
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Temp._ROOT))) {
            File defaultDirectory = new File(configure.getWorkDirectoryPath(), Constants.Configure.Directory.TEMP);
            configure.putConfig(Constants.Configure.Keys.Directory.Temp._ROOT, FileUtil.getCanonicalPath(defaultDirectory));
//            configure.putConfig(Constants.Configure.Keys.Directory.Temp._ROOT, FileUtil.getTempDirectoryPath());
        }
        String defaultTempDirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Temp._ROOT);

        // =========================================== upload ===========================================
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Temp.Upload._ROOT))) {
            File defaultDirectory = new File(defaultTempDirectoryName, Constants.Configure.Directory.UPLOAD);
            configure.putConfig(Constants.Configure.Keys.Directory.Temp.Upload._ROOT, FileUtil.getCanonicalPath(defaultDirectory));
        }
        String defaultUploadDirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Temp.Upload._ROOT);

        // standalone_v1
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Temp.Upload.OFFLINE_V1))) {
            File defaultDirectory = new File(defaultUploadDirectoryName, Constants.Configure.Directory.STANDALONE_V1);
            configure.putConfig(Constants.Configure.Keys.Directory.Temp.Upload.OFFLINE_V1, FileUtil.getCanonicalPath(defaultDirectory));
        }
//        String defaultOfflineV1DirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Temp.Upload.OFFLINE_V1);

        // standalone_v3
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Temp.Upload.OFFLINE_V3))) {
            File defaultDirectory = new File(defaultUploadDirectoryName, Constants.Configure.Directory.STANDALONE_V3);
            configure.putConfig(Constants.Configure.Keys.Directory.Temp.Upload.OFFLINE_V3, FileUtil.getCanonicalPath(defaultDirectory));
        }
//        String defaultOfflineV3DirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Temp.Upload.OFFLINE_V3);

        // =========================================== download ===========================================
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Temp.Download._ROOT))) {
            File defaultDirectory = new File(defaultTempDirectoryName, Constants.Configure.Directory.DOWNLOAD);
            configure.putConfig(Constants.Configure.Keys.Directory.Temp.Download._ROOT, FileUtil.getCanonicalPath(defaultDirectory));
        }
        String defaultDownloadDirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Temp.Download._ROOT);

        // standalone_v1
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Temp.Download.OFFLINE_V1))) {
            File defaultDirectory = new File(defaultDownloadDirectoryName, Constants.Configure.Directory.STANDALONE_V1);
            configure.putConfig(Constants.Configure.Keys.Directory.Temp.Download.OFFLINE_V1, FileUtil.getCanonicalPath(defaultDirectory));
        }
//        String defaultDownloadOfflineV1DirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Temp.Download.OFFLINE_V1);

        // standalone_v3
        if (StringUtil.isBlank(configure.getConfig(Constants.Configure.Keys.Directory.Temp.Download.OFFLINE_V3))) {
            File defaultDirectory = new File(defaultDownloadDirectoryName, Constants.Configure.Directory.STANDALONE_V3);
            configure.putConfig(Constants.Configure.Keys.Directory.Temp.Download.OFFLINE_V3, FileUtil.getCanonicalPath(defaultDirectory));
        }
//        String defaultDownloadOfflineV3DirectoryName = configure.getConfig(Constants.Configure.Keys.Directory.Temp.Download.OFFLINE_V3);
    }

    private void loadUIDataFromProperties(Properties properties, String key, Configure configure) {
        String json = properties.getProperty(key);
        List<ItemKeyValue> keyValueList = JsonUtil.toList(json, ItemKeyValue.class);
        ItemKeyValue[] keyValueArray = keyValueList.toArray(new ItemKeyValue[0]);
        configure.putUIData(key, keyValueArray);
    }

    private void loadConfigFromProperties(Properties properties, String key, Configure configure) {
        String value = properties.getProperty(key);
        configure.putConfig(key, value);
    }
}
