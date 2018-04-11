/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log.gui
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 11:04
 */
package cn.savor.standalone.log.gui;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <h1>下拉列表 [监听器] - 应用配置</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月11日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@RequiredArgsConstructor
public class ComboBoxActionListener implements ActionListener {

    @NonNull
    private Context context;

    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();
        if (object instanceof JComboBox) {
            JComboBox comboBox = (JComboBox) object;
            String configName = comboBox.getName();
            ItemKeyValue itemKeyValue = (ItemKeyValue) comboBox.getSelectedItem();
            context.setConfig(configName, (String) itemKeyValue.getKey());
        }
    }
}
