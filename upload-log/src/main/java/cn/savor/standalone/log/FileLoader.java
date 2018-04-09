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

import cn.savor.standalone.log.exception.LoadException;
import net.lizhaoweb.common.util.base.Constant;
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

    private static final String USER_HOME_CONFIG_DIRECTORY_NAME = ".offline_logs";
    private static final String USER_WORK_CONFIG_DIRECTORY_NAME = "conf";

    protected Logger logger = LoggerFactory.getLogger(FileLoader.class);

    public Properties loadDataFile() {
        Properties properties = new Properties();
        String fileName = "data.properties";
        if (this.loadFromUserHome(properties, fileName)) {
            return properties;
        }
        if (this.loadFromUserWork(properties, fileName)) {
            return properties;
        }
        fileName = "app-jar-data.properties";
        if (this.loadFromUserHome(properties, fileName)) {
            return properties;
        }
        if (this.loadFromUserWork(properties, fileName)) {
            return properties;
        }
        this.loadFromJar(properties, fileName);
        return properties;
    }

    private boolean loadFromUserHome(Properties properties, String fileName) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            String userHome = System.getProperty("user.home");
            String configFileName = String.format("%s/%s/%s", userHome, USER_HOME_CONFIG_DIRECTORY_NAME, fileName);
            inputStream = new BufferedInputStream(new FileInputStream(configFileName));
            inputStreamReader = new InputStreamReader(inputStream, Constant.Charset.UTF8);
            properties.load(inputStreamReader);
            return true;
        } catch (IOException e) {
            logger.warn(e.getMessage());
        } finally {
            IOUtil.close(inputStreamReader);
            IOUtil.close(inputStream);
        }
        return false;
    }

    private boolean loadFromUserWork(Properties properties, String fileName) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            String userWork = System.getProperty("user.dir");
            String configFileName = String.format("%s/../%s/%s", userWork, USER_WORK_CONFIG_DIRECTORY_NAME, fileName);
            inputStream = new BufferedInputStream(new FileInputStream(configFileName));
            inputStreamReader = new InputStreamReader(inputStream, Constant.Charset.UTF8);
            properties.load(inputStreamReader);
            return true;
        } catch (IOException e) {
            logger.warn(e.getMessage());
        } finally {
            IOUtil.close(inputStreamReader);
            IOUtil.close(inputStream);
        }
        return false;
    }

    private boolean loadFromJar(Properties properties, String fileName) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            String configFileName = String.format("/config/offline_logs/%s", fileName);
            inputStream = this.getClass().getResourceAsStream(configFileName);
            inputStreamReader = new InputStreamReader(inputStream, Constant.Charset.UTF8);
            properties.load(inputStreamReader);
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
