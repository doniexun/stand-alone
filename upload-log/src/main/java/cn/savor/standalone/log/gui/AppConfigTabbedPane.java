/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log.gui
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 17:01
 */
package cn.savor.standalone.log.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月05日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
class AppConfigTabbedPane {

    void buildUI(Container parentContainer) {
        JScrollPane mainPanel = new JScrollPane();
        mainPanel.setSize(parentContainer.getWidth(), parentContainer.getHeight());
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel rowPanel_1 = new JPanel();
//        rowPanel_1.setSize(mainPanel.getWidth(), 20);
        rowPanel_1.setPreferredSize(new Dimension(mainPanel.getWidth(), 20));
        rowPanel_1.setLayout(new GridLayout(1, 5));

        ItemKeyValue[] areaItemData = {
                new ItemKeyValue<String, String>("010", "北京市"),
                new ItemKeyValue<String, String>("020", "广州市"),
                new ItemKeyValue<String, String>("021", "上海市"),
                new ItemKeyValue<String, String>("0755", "深圳市")
        };
        JLabel areaLabel = new JLabel("城市");
        JComboBox<ItemKeyValue> areaComboBox = new JComboBox<>(areaItemData);
        rowPanel_1.add(areaLabel);
        rowPanel_1.add(areaComboBox);

        rowPanel_1.add(Box.createHorizontalGlue());
//        rowPanel_1.add(Box.createHorizontalStrut(10));

        ItemKeyValue[] ossBucketItemData = {
                new ItemKeyValue<String, String>("redian-produce", "生产环境"),
                new ItemKeyValue<String, String>("redian-development", "测试环境")
        };
        JLabel ossBucketLabel = new JLabel("OSS 环境");
        JComboBox<ItemKeyValue> ossBucketComboBox = new JComboBox<>(ossBucketItemData);
        rowPanel_1.add(ossBucketLabel);
        rowPanel_1.add(ossBucketComboBox);

        mainPanel.add(rowPanel_1);
        mainPanel.add(new JPanel());
        mainPanel.repaint();
        parentContainer.add(mainPanel, WindowConstant.AppTabbedPane.Config.title);
    }


}

