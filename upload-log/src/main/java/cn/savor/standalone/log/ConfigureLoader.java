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
package cn.savor.standalone.log;


import cn.savor.standalone.log.exception.LoadException;
import cn.savor.standalone.log.gui.ItemKeyValue;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.lizhaoweb.common.util.base.JsonUtil;

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
    private Configure configure;

    @NonNull
    private FileLoader fileLoader;

    public ConfigureLoader() {
        configure = new Configure();
        fileLoader = new FileLoader();
    }

    public Configure loadUIData() throws LoadException {
        Properties properties = fileLoader.loadProperties();
        this.loadUIDataFromProperties(properties, Constants.Properties.Keys.CITY, configure);
        this.loadUIDataFromProperties(properties, Constants.Properties.Keys.OSS_BUCKET, configure);
        this.loadUIDataFromProperties(properties, Constants.Properties.Keys.OSS_OBJECT_KEY, configure);
        return configure;
    }

    private void loadUIDataFromProperties(Properties properties, String key, Configure configure) {
        String json = properties.getProperty(key);
        List<ItemKeyValue> keyValueList = JsonUtil.toList(json, ItemKeyValue.class);
        ItemKeyValue[] keyValueArray = keyValueList.toArray(new ItemKeyValue[0]);
        configure.putUIData(key, keyValueArray);
    }
}
