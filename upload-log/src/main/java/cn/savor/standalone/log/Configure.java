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
package cn.savor.standalone.log;

import cn.savor.standalone.log.gui.ItemKeyValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
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
    private String userWork;

    @Setter
    @Getter
    private String dataFile;

    @Setter
    @Getter
    private String configFile;

    public Configure() {
        this.userWork = System.getProperty("user.dir");
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
        this.configMap.put(key, value);
    }

    public String getConfig(String key) {
        return this.configMap.get(key);
    }
}
