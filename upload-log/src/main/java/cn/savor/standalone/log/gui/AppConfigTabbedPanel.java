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
import net.lizhaoweb.common.util.base.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

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
class AppConfigTabbedPanel {

    protected Logger logger = LoggerFactory.getLogger(AppConfigTabbedPanel.class);

    @NonNull
    private ApplicationContext context;

    Component buildUI(Container parentContainer) {
        JPanel mainPanel = new JPanel();

        mainPanel.setSize((int) (parentContainer.getWidth() * 0.98), parentContainer.getHeight());
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setLayout(new BorderLayout());

        JPanel centerPanel = this.createCenterPanel(mainPanel);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        JPanel southPanel = this.createSouthPanel();
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    void close() {
    }

    private JPanel createSouthPanel() {
        JPanel southPanel = new JPanel();
        JButton savorConfigButton = new JButton(WindowConstant.AppTabbedPanel.Config.SaveButton.label);
        savorConfigButton.setToolTipText(WindowConstant.AppTabbedPanel.Config.SaveButton.tip + context.getConfigFilePath());
        savorConfigButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                OutputStream outputStream = null;
                try {
                    File configFile = new File(context.getConfigFilePath());
                    if (!configFile.getParentFile().exists()) {
                        boolean isMake = configFile.getParentFile().mkdirs();
                        if (!isMake) {
                            String exceptionMessage = String.format("The directory[%s] is not make", configFile.getParentFile());
                            throw new IllegalStateException(exceptionMessage);
                        }
                    } else if (!configFile.getParentFile().isDirectory()) {
                        boolean isDelete = configFile.getParentFile().delete();
                        if (!isDelete) {
                            String exceptionMessage = String.format("The file[%s] is not delete", configFile.getParentFile());
                            throw new IllegalStateException(exceptionMessage);
                        }
                        boolean isMake = configFile.getParentFile().mkdirs();
                        if (!isMake) {
                            String exceptionMessage = String.format("The directory[%s] is not make", configFile.getParentFile());
                            throw new IllegalStateException(exceptionMessage);
                        }
                    }
                    outputStream = new FileOutputStream(configFile);
                    Set<Map.Entry<String, String>> entrySet = context.getConfigs();
                    for (Map.Entry<String, String> entry : entrySet) {
                        String key = entry.getKey();
                        String value = entry.getValue().replace("\\", "\\\\").replace(":", "\\:");
                        String configKeyValue = String.format("%s=%s\n", key, value);
                        outputStream.write(configKeyValue.getBytes());
                    }
                    outputStream.flush();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                } finally {
                    IOUtil.close(outputStream);
                }
            }
        });
        southPanel.add(savorConfigButton);
        return southPanel;
    }

    private JPanel createCenterPanel(JPanel mainPanel) {
        JPanel centerPanel = new JPanel();
        centerPanel.setSize(mainPanel.getWidth(), mainPanel.getHeight());

        //======================= 第一行
        JPanel rowPanel_1 = new JPanel();
        rowPanel_1.setPreferredSize(new Dimension(centerPanel.getWidth(), 20));
        rowPanel_1.setLayout(new BoxLayout(rowPanel_1, BoxLayout.X_AXIS));
        this.structureUIForCity(rowPanel_1);
        rowPanel_1.add(Box.createHorizontalGlue());
        this.structureUIForOSSBucket(rowPanel_1);
        rowPanel_1.add(Box.createHorizontalGlue());
        this.structureUIForOSSObjectKey(rowPanel_1);


        //======================= 第二行
        JPanel rowPanel_2 = new JPanel();
        rowPanel_2.setPreferredSize(new Dimension(centerPanel.getWidth(), 20));
        rowPanel_2.setLayout(new BoxLayout(rowPanel_2, BoxLayout.X_AXIS));
        this.structureUIForTempDir(rowPanel_2);
        rowPanel_2.add(Box.createHorizontalGlue());


        //======================= 第三行
        JPanel rowPanel_3 = new JPanel();
        rowPanel_3.setPreferredSize(new Dimension(centerPanel.getWidth(), 20));
        rowPanel_3.setLayout(new BoxLayout(rowPanel_3, BoxLayout.X_AXIS));
        this.structureUIForDataDir(rowPanel_3);
        rowPanel_3.add(Box.createHorizontalGlue());


        centerPanel.add(rowPanel_1, BorderLayout.CENTER);
        centerPanel.add(rowPanel_2, BorderLayout.CENTER);
        centerPanel.add(rowPanel_3, BorderLayout.CENTER);
        return centerPanel;
    }

    private void structureUIForDataDir(JPanel rowPanel_3) {
        JLabel localDataDirLabel = new JLabel(WindowConstant.AppTabbedPanel.Config.DataDirectory.label);
        localDataDirLabel.setPreferredSize(new Dimension(60, 0));
        rowPanel_3.add(localDataDirLabel);

        JTextField localDataDirField = new JTextField();
        localDataDirField.setEnabled(false);
        localDataDirField.setForeground(new Color(20, 20, 20));
        localDataDirField.setDisabledTextColor(new Color(30, 30, 30));
        localDataDirField.setBackground(new Color(220, 220, 220));
        localDataDirField.setName(WindowConstant.AppTabbedPanel.Config.DataDirectory.fieldName);
        localDataDirField.setText(context.getConfig(WindowConstant.AppTabbedPanel.Config.DataDirectory.fieldName));
        rowPanel_3.add(localDataDirField);

        JButton localDataDirButton = new JButton(WindowConstant.AppTabbedPanel.Config.DataDirectory.fileChooseButton);
        localDataDirButton.setPreferredSize(new Dimension(20, 0));
        localDataDirButton.addActionListener(new FileChooserActionListener(context, localDataDirField, 1));
        rowPanel_3.add(localDataDirButton);
    }

    private void structureUIForTempDir(JPanel rowPanel_2) {
        JLabel localTempDirLabel = new JLabel(WindowConstant.AppTabbedPanel.Config.TempDirectory.label);
        localTempDirLabel.setPreferredSize(new Dimension(60, 0));
        rowPanel_2.add(localTempDirLabel);

        JTextField localTempDirField = new JTextField();
        localTempDirField.setEnabled(false);
        localTempDirField.setForeground(new Color(20, 20, 20));
        localTempDirField.setDisabledTextColor(new Color(30, 30, 30));
        localTempDirField.setBackground(new Color(220, 220, 220));
        localTempDirField.setName(WindowConstant.AppTabbedPanel.Config.TempDirectory.fieldName);
        localTempDirField.setText(context.getConfig(WindowConstant.AppTabbedPanel.Config.TempDirectory.fieldName));
        rowPanel_2.add(localTempDirField);

        JButton localTempDirButton = new JButton(WindowConstant.AppTabbedPanel.Config.TempDirectory.fileChooseButton);
        localTempDirButton.setPreferredSize(new Dimension(20, 0));
        localTempDirButton.addActionListener(new FileChooserActionListener(context, localTempDirField, 1));
        rowPanel_2.add(localTempDirButton);
    }

    private void structureUIForOSSObjectKey(JPanel rowPanel_1) {
        JLabel ossObjectKeyLabel = new JLabel(WindowConstant.AppTabbedPanel.Config.OSSObjectKey.label);
        ossObjectKeyLabel.setPreferredSize(new Dimension(60, 0));
        rowPanel_1.add(ossObjectKeyLabel);

        ItemKeyValue ossObjectKeySelectedItem = null;
        ItemKeyValue[] ossObjectKeyItemData = context.getUIData(Constants.Properties.Keys.OSS_OBJECT_KEY);
        for (ItemKeyValue itemKeyValue : ossObjectKeyItemData) {
            if (itemKeyValue.getKey().equals(context.getConfig(WindowConstant.AppTabbedPanel.Config.OSSObjectKey.fieldName))) {
                ossObjectKeySelectedItem = itemKeyValue;
                break;
            }
        }
        JComboBox<ItemKeyValue> ossObjectKeyComboBox = new JComboBox<>(ossObjectKeyItemData);
        ossObjectKeyComboBox.setName(WindowConstant.AppTabbedPanel.Config.OSSObjectKey.fieldName);
        ossObjectKeyComboBox.addActionListener(new ComboBoxActionListener(context));
        ossObjectKeyComboBox.setSelectedItem(ossObjectKeySelectedItem);
        rowPanel_1.add(ossObjectKeyComboBox);
    }

    private void structureUIForOSSBucket(JPanel rowPanel_1) {
        JLabel ossBucketLabel = new JLabel(WindowConstant.AppTabbedPanel.Config.OSSBucket.label);
        ossBucketLabel.setPreferredSize(new Dimension(60, 0));
        rowPanel_1.add(ossBucketLabel);

        ItemKeyValue ossBucketSelectedItem = null;
        ItemKeyValue[] ossBucketItemData = context.getUIData(Constants.Properties.Keys.OSS_BUCKET);
        for (ItemKeyValue itemKeyValue : ossBucketItemData) {
            if (itemKeyValue.getKey().equals(context.getConfig(WindowConstant.AppTabbedPanel.Config.OSSBucket.fieldName))) {
                ossBucketSelectedItem = itemKeyValue;
                break;
            }
        }
        JComboBox<ItemKeyValue> ossBucketComboBox = new JComboBox<>(ossBucketItemData);
        ossBucketComboBox.setName(WindowConstant.AppTabbedPanel.Config.OSSBucket.fieldName);
        ossBucketComboBox.addActionListener(new ComboBoxActionListener(context));
        ossBucketComboBox.setSelectedItem(ossBucketSelectedItem);
        rowPanel_1.add(ossBucketComboBox);
    }

    private void structureUIForCity(JPanel rowPanel_1) {
        JLabel areaLabel = new JLabel(WindowConstant.AppTabbedPanel.Config.City.label);
        areaLabel.setPreferredSize(new Dimension(60, 0));
        rowPanel_1.add(areaLabel);

        ItemKeyValue areaSelectedItem = null;
        ItemKeyValue[] areaItemData = context.getUIData(Constants.Properties.Keys.CITY);
        for (ItemKeyValue itemKeyValue : areaItemData) {
            if (itemKeyValue.getKey().equals(context.getConfig(WindowConstant.AppTabbedPanel.Config.City.fieldName))) {
                areaSelectedItem = itemKeyValue;
                break;
            }
        }
        JComboBox<ItemKeyValue> areaComboBox = new JComboBox<>(areaItemData);
        areaComboBox.setName(WindowConstant.AppTabbedPanel.Config.City.fieldName);
        areaComboBox.addActionListener(new ComboBoxActionListener(context));
        areaComboBox.setSelectedItem(areaSelectedItem);
        rowPanel_1.add(areaComboBox);
    }
}
