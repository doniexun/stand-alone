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
package cn.savor.standalone.log.util;

import cn.savor.standalone.log.model.Configure;
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
        String fileName = System.getProperty("savor.tool.box.conf");
        logger.info("System.getProperty(\"savor.tool.box.conf\")={}", fileName);
        if (this.loadFromFile(configure, properties, fileName, FILE_TYPE_CONFIG)) {
            return properties;
        }
        fileName = "config.properties";
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
        String configFileName = String.format("%s/%s/%s", FileUtil.getUserDirectoryPath(), Constants.Configure.Directory.USER_HOME, fileName);
        return this.loadFromFile(configure, properties, configFileName, fileType);
    }

    private boolean loadFromUserWork(Configure configure, Properties properties, String fileName, int fileType) {
        String configFileName = String.format("%s/%s/%s", configure.getWorkDirectoryPath(), Constants.Configure.Directory.USER_WORK, fileName);
        return this.loadFromFile(configure, properties, configFileName, fileType);
    }

    private boolean loadFromJar(Configure configure, Properties properties, String fileName, int fileType) {
        String configFileName = String.format("/config/offline_logs/%s", fileName);
        String defaultFileName = String.format("%s/%s/%s", FileUtil.getUserDirectoryPath(), Constants.Configure.Directory.USER_HOME, fileName);
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            inputStream = this.getClass().getResourceAsStream(configFileName);
            inputStreamReader = new InputStreamReader(inputStream, Constant.Charset.UTF8);
            properties.load(inputStreamReader);
            File configFile = new File(defaultFileName);
            this.recordConfigPath(configure, fileType, configFile);
            return true;
        } catch (IOException e) {
            logger.warn(e.getMessage());
        } finally {
            IOUtil.close(inputStreamReader);
            IOUtil.close(inputStream);
        }
        return false;
    }

    private boolean loadFromFile(Configure configure, Properties properties, String configFileName, int fileType) {
        FileInputStream fileInputStream = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            File configFile = new File(configFileName);
            if (!configFile.exists() || !configFile.isFile()) {
                return false;
            }
            fileInputStream = new FileInputStream(configFile);
            inputStream = new BufferedInputStream(fileInputStream);
            inputStreamReader = new InputStreamReader(inputStream, Constant.Charset.UTF8);
            properties.load(inputStreamReader);
            this.recordConfigPath(configure, fileType, configFile);
            return true;
        } catch (IOException e) {
            logger.warn(e.getMessage());
        } finally {
            IOUtil.close(inputStreamReader);
            IOUtil.close(inputStream);
            IOUtil.close(fileInputStream);
        }
        return false;
    }

    private void recordConfigPath(Configure configure, int fileType, File configFile) {
        if (fileType == FILE_TYPE_DATA) {
            String realDataFileName = FileUtil.getCanonicalPath(configFile);
            logger.info("dataFilePath = {}", realDataFileName);
            configure.setDataFilePath(realDataFileName);
        } else if (fileType == FILE_TYPE_CONFIG) {
            String realConfigFileName = FileUtil.getCanonicalPath(configFile);
            logger.info("configFilePath = {}", realConfigFileName);
            configure.setConfigFilePath(realConfigFileName);
        }
    }
}
