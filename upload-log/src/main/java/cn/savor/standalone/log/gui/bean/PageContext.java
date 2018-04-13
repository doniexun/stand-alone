/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log.gui
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 11:37
 */
package cn.savor.standalone.log.gui.bean;

import cn.savor.standalone.log.model.ItemKeyValue;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.lizhaoweb.common.util.base.ArrayUtil;

import javax.swing.*;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月12日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@RequiredArgsConstructor
public class PageContext {

    private Map<String, String[]> parameterMap = new ConcurrentHashMap<>();

    @NonNull
    @Getter
    private ApplicationContext applicationContext;

    public JFrame getTopWindow() {
        return applicationContext.getTopWindow();
    }

    public JFileChooser getFileChooser() {
        return applicationContext.getFileChooser();
    }

    public void setConfig(String name, String value) {
        applicationContext.setConfig(name, value);
    }

    public String getConfig(String name) {
        return applicationContext.getConfig(name);
    }

    public ItemKeyValue[] getUIData(String key) {
        return applicationContext.getUIData(key);
    }

    public Set<Map.Entry<String, String>> getConfigs() {
        return applicationContext.getConfigs();
    }

    public String getConfigFilePath() {
        return applicationContext.getConfigFilePath();
    }

    public void setParameters(String name, String[] values) {
        parameterMap.put(name, values);
    }

    public void setParameter(String name, String value) {
        String[] values = {value};
        parameterMap.put(name, values);
    }

    public void addParameter(String name, String value) {
        String[] values = this.getParameters(name);
        if (values == null) {
            values = new String[]{};
        }
        values = ArrayUtil.add(values, value);
        parameterMap.put(name, values);
    }

    public String[] getParameters(String name) {
        return parameterMap.get(name);
    }

    public String getParameter(String name) {
        String[] values = this.getParameters(name);
        if (values != null && values.length > 0) {
            return values[0];
        }
        return null;
    }

    public String[] removeParameters(String name) {
        return parameterMap.remove(name);
    }
}
