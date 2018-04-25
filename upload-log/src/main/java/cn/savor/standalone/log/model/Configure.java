/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:42
 */
package cn.savor.standalone.log.model;

import lombok.Getter;
import lombok.Setter;
import net.lizhaoweb.common.util.base.FileUtil;

import java.io.File;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h1>模型 - 配置</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月09日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class Configure {

    private Map<String, ItemKeyValue[]> uiDataMap;

    private Map<String, String> configMap;

    @Getter
    private String workDirectoryPath;

    @Getter
    private String currentWorkDirectoryPath;

    @Setter
    @Getter
    private String dataFilePath;

    @Setter
    @Getter
    private String configFilePath;

    public Configure() {
        this.currentWorkDirectoryPath = System.getProperty("user.dir");
//        this.workDirectoryPath = FileUtil.getCanonicalPath(new File(this.currentWorkDirectoryPath, ".."));
        this.workDirectoryPath = FileUtil.getCanonicalPath(new File(this.currentWorkDirectoryPath));
        this.uiDataMap = new ConcurrentHashMap<>();
        this.configMap = new ConcurrentHashMap<>();
    }

    public void putUIData(String key, ItemKeyValue[] value) {
        this.uiDataMap.put(key, value);
    }

    public ItemKeyValue[] getUIData(String key) {
        return this.uiDataMap.get(key);
    }

    public void putConfig(String key, String value) {
        if (value == null) {
            return;
        }
        this.configMap.put(key, value);
    }

    public String getConfig(String key) {
        return this.configMap.get(key);
    }

    public Set<Map.Entry<String, String>> getConfigs() {
        Map<String, String> sortMap = new TreeMap<>(new StringComparator());
        sortMap.putAll(configMap);
        return sortMap.entrySet();
    }

    /**
     * 字符串比较器。
     */
    private static class StringComparator implements Comparator<String> {
        @Override
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
    }
}
