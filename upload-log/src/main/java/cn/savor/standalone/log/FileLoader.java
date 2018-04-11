/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : standalone
 * @Package : cn.savor.standalone.log
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 22:35
 */
package cn.savor.standalone.log;

import net.lizhaoweb.common.util.base.Constant;
import net.lizhaoweb.common.util.base.FileUtil;
import net.lizhaoweb.common.util.base.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * <h1>加载器 - 文件</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月05日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class FileLoader {

    private static final int FILE_TYPE_DATA = 1;
    private static final int FILE_TYPE_CONFIG = 0;

    protected Logger logger = LoggerFactory.getLogger(FileLoader.class);

    public Properties loadDataFile(Configure configure) {
        Properties properties = new Properties();
        String fileName = "data.properties";
        if (this.loadFromUserHome(configure, properties, fileName, FILE_TYPE_DATA)) {
            return properties;
        }
        if (this.loadFromUserWork(configure, properties, fileName, FILE_TYPE_DATA)) {
            return properties;
        }
        fileName = "app-jar-data.properties";
        if (this.loadFromUserHome(configure, properties, fileName, FILE_TYPE_DATA)) {
            return properties;
        }
        if (this.loadFromUserWork(configure, properties, fileName, FILE_TYPE_DATA)) {
            return properties;
        }
        this.loadFromJar(configure, properties, fileName, FILE_TYPE_DATA);
        return properties;
    }

    public Properties loadConfigFile(Configure configure) {
        Properties properties = new Properties();
        String fileName = "config.properties";
        if (this.loadFromUserHome(configure, properties, fileName, FILE_TYPE_CONFIG)) {
            return properties;
        }
        if (this.loadFromUserWork(configure, properties, fileName, FILE_TYPE_CONFIG)) {
            return properties;
        }
        fileName = "app-jar-config.properties";
        if (this.loadFromUserHome(configure, properties, fileName, FILE_TYPE_CONFIG)) {
            return properties;
        }
        if (this.loadFromUserWork(configure, properties, fileName, FILE_TYPE_CONFIG)) {
            return properties;
        }
        this.loadFromJar(configure, properties, fileName, FILE_TYPE_CONFIG);
        return properties;
    }

    private boolean loadFromUserHome(Configure configure, Properties properties, String fileName, int fileType) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            String configFileName = String.format("%s/%s/%s", FileUtil.getUserDirectoryPath(), Constants.Configure.Directory.USER_HOME, fileName);
            File configFile = new File(configFileName);
            if (!configFile.exists() || !configFile.isFile()) {
                return false;
            }
            inputStream = new BufferedInputStream(new FileInputStream(configFile));
            inputStreamReader = new InputStreamReader(inputStream, Constant.Charset.UTF8);
            properties.load(inputStreamReader);
            if (fileType == FILE_TYPE_DATA) {
                configure.setDataFilePath(FileUtil.getCanonicalPath(configFile));
            } else if (fileType == FILE_TYPE_CONFIG) {
                configure.setConfigFilePath(FileUtil.getCanonicalPath(configFile));
            }
            return true;
        } catch (IOException e) {
            logger.warn(e.getMessage());
        } finally {
            IOUtil.close(inputStreamReader);
            IOUtil.close(inputStream);
        }
        return false;
    }

    private boolean loadFromUserWork(Configure configure, Properties properties, String fileName, int fileType) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            String configFileName = String.format("%s/%s/%s", configure.getWorkDirectoryPath(), Constants.Configure.Directory.USER_WORK, fileName);
            File configFile = new File(configFileName);
            if (!configFile.exists() || !configFile.isFile()) {
                return false;
            }
            inputStream = new BufferedInputStream(new FileInputStream(configFile));
            inputStreamReader = new InputStreamReader(inputStream, Constant.Charset.UTF8);
            properties.load(inputStreamReader);
            if (fileType == FILE_TYPE_DATA) {
                configure.setDataFilePath(FileUtil.getCanonicalPath(configFile));
            } else if (fileType == FILE_TYPE_CONFIG) {
                configure.setConfigFilePath(FileUtil.getCanonicalPath(configFile));
            }
            return true;
        } catch (IOException e) {
            logger.warn(e.getMessage());
        } finally {
            IOUtil.close(inputStreamReader);
            IOUtil.close(inputStream);
        }
        return false;
    }

    private boolean loadFromJar(Configure configure, Properties properties, String fileName, int fileType) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            String configFileName = String.format("/config/offline_logs/%s", fileName);
            inputStream = this.getClass().getResourceAsStream(configFileName);
            inputStreamReader = new InputStreamReader(inputStream, Constant.Charset.UTF8);
            properties.load(inputStreamReader);
            String defaultFileName = String.format("%s/%s/%s", FileUtil.getUserDirectoryPath(), Constants.Configure.Directory.USER_HOME, fileName);
            File configFile = new File(defaultFileName);
            if (fileType == FILE_TYPE_DATA) {
                configure.setDataFilePath(FileUtil.getCanonicalPath(configFile));
            } else if (fileType == FILE_TYPE_CONFIG) {
                configure.setConfigFilePath(FileUtil.getCanonicalPath(configFile));
            }
            return true;
        } catch (IOException e) {
            logger.warn(e.getMessage());
        } finally {
            IOUtil.close(inputStreamReader);
            IOUtil.close(inputStream);
        }
        return false;
    }
}
