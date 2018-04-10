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

import cn.savor.standalone.log.Constants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
@RequiredArgsConstructor
class AppConfigTabbedPane {

    @NonNull
    private Context context;

    Component buildUI(Container parentContainer) {
        JPanel mainPanel = new JPanel();

        mainPanel.setSize((int) (parentContainer.getWidth() * 0.98), parentContainer.getHeight());
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //======================= 第一行
        JPanel rowPanel_1 = new JPanel();
        rowPanel_1.setPreferredSize(new Dimension(mainPanel.getWidth(), 20));
        rowPanel_1.setLayout(new BoxLayout(rowPanel_1, BoxLayout.X_AXIS));
        this.buildUIForCity(rowPanel_1);
        rowPanel_1.add(Box.createHorizontalGlue());
        this.buildUIForOSSBucket(rowPanel_1);
        rowPanel_1.add(Box.createHorizontalGlue());
        this.buildUIForOSSObjectKey(rowPanel_1);


        //======================= 第二行
        JPanel rowPanel_2 = new JPanel();
        rowPanel_2.setPreferredSize(new Dimension(mainPanel.getWidth(), 20));
        rowPanel_2.setLayout(new BoxLayout(rowPanel_2, BoxLayout.X_AXIS));
        this.buildUIForTempDir(rowPanel_2);
        rowPanel_2.add(Box.createHorizontalGlue());


        //======================= 第三行
        JPanel rowPanel_3 = new JPanel();
        rowPanel_3.setPreferredSize(new Dimension(mainPanel.getWidth(), 20));
        rowPanel_3.setLayout(new BoxLayout(rowPanel_3, BoxLayout.X_AXIS));
        this.buildUIForDataDir(rowPanel_3);
        rowPanel_3.add(Box.createHorizontalGlue());


        mainPanel.add(rowPanel_1, BorderLayout.CENTER);
        mainPanel.add(rowPanel_2, BorderLayout.CENTER);
        mainPanel.add(rowPanel_3, BorderLayout.CENTER);
//        mainPanel.add(new JPanel());
//        mainPanel.repaint();
        mainPanel.add(new JButton("保存"), BorderLayout.SOUTH);
        parentContainer.add(mainPanel, WindowConstant.AppTabbedPane.Config.title);
        return mainPanel;
    }

    private void buildUIForDataDir(JPanel rowPanel_3) {
        JLabel localDataDirLabel = new JLabel("数据目录");
        localDataDirLabel.setPreferredSize(new Dimension(60, 0));
        rowPanel_3.add(localDataDirLabel);

        JTextField localDataDirField = new JTextField();
        localDataDirField.setText(context.getConfig(Constants.Configure.Keys.DIRECTORY_DATA));
        rowPanel_3.add(localDataDirField);

        JButton localDataDirButton = new JButton("...");
        localDataDirButton.setPreferredSize(new Dimension(20, 0));
        localDataDirButton.addActionListener(new FileChooserActionListener(context, localDataDirField, 1));
        rowPanel_3.add(localDataDirButton);
    }

    private void buildUIForTempDir(JPanel rowPanel_2) {
        JLabel localTempDirLabel = new JLabel("临时目录");
        localTempDirLabel.setPreferredSize(new Dimension(60, 0));
        rowPanel_2.add(localTempDirLabel);

        JTextField localTempDirField = new JTextField();
        localTempDirField.setText(context.getConfig(Constants.Configure.Keys.DIRECTORY_TEMP));
        rowPanel_2.add(localTempDirField);

        JButton localTempDirButton = new JButton("...");
        localTempDirButton.setPreferredSize(new Dimension(20, 0));
        localTempDirButton.addActionListener(new FileChooserActionListener(context, localTempDirField, 1));
        rowPanel_2.add(localTempDirButton);
    }

    private void buildUIForOSSObjectKey(JPanel rowPanel_1) {
        JLabel ossObjectKeyLabel = new JLabel("OSS 前缀");
        ossObjectKeyLabel.setPreferredSize(new Dimension(60, 0));
        rowPanel_1.add(ossObjectKeyLabel);

        ItemKeyValue ossObjectKeySelectedItem = null;
        ItemKeyValue[] ossObjectKeyItemData = context.getUIData(Constants.Properties.Keys.OSS_OBJECT_KEY);
        for (ItemKeyValue itemKeyValue : ossObjectKeyItemData) {
            if (itemKeyValue.getKey().equals(context.getConfig(Constants.Configure.Keys.OSS_OBJECT_KEY))) {
                ossObjectKeySelectedItem = itemKeyValue;
                break;
            }
        }
        JComboBox<ItemKeyValue> ossObjectKeyComboBox = new JComboBox<>(ossObjectKeyItemData);
        ossObjectKeyComboBox.setSelectedItem(ossObjectKeySelectedItem);
        rowPanel_1.add(ossObjectKeyComboBox);
    }

    private void buildUIForOSSBucket(JPanel rowPanel_1) {
        JLabel ossBucketLabel = new JLabel("OSS 环境");
        ossBucketLabel.setPreferredSize(new Dimension(60, 0));
        rowPanel_1.add(ossBucketLabel);

        ItemKeyValue ossBucketSelectedItem = null;
        ItemKeyValue[] ossBucketItemData = context.getUIData(Constants.Properties.Keys.OSS_BUCKET);
        for (ItemKeyValue itemKeyValue : ossBucketItemData) {
            if (itemKeyValue.getKey().equals(context.getConfig(Constants.Configure.Keys.OSS_BUCKET))) {
                ossBucketSelectedItem = itemKeyValue;
                break;
            }
        }
        JComboBox<ItemKeyValue> ossBucketComboBox = new JComboBox<>(ossBucketItemData);
        ossBucketComboBox.setSelectedItem(ossBucketSelectedItem);
        rowPanel_1.add(ossBucketComboBox);
    }

    private void buildUIForCity(JPanel rowPanel_1) {
        JLabel areaLabel = new JLabel("城市");
        areaLabel.setPreferredSize(new Dimension(60, 0));
        rowPanel_1.add(areaLabel);

        ItemKeyValue areaSelectedItem = null;
        ItemKeyValue[] areaItemData = context.getUIData(Constants.Properties.Keys.CITY);
        for (ItemKeyValue itemKeyValue : areaItemData) {
            if (itemKeyValue.getKey().equals(context.getConfig(Constants.Configure.Keys.CITY))) {
                areaSelectedItem = itemKeyValue;
                break;
            }
        }
        JComboBox<ItemKeyValue> areaComboBox = new JComboBox<>(areaItemData);
        areaComboBox.setSelectedItem(areaSelectedItem);
        rowPanel_1.add(areaComboBox);
    }
}
