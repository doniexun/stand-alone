/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log.gui
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 10:53
 */
package cn.savor.standalone.log.gui.listener;

import cn.savor.standalone.log.util.Constants;
import cn.savor.standalone.log.gui.bean.PageContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.lizhaoweb.common.util.base.FileUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * <h1>文件选择器 [监听器] - 应用配置</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@RequiredArgsConstructor
public class FileChooserActionListener implements ActionListener {

    @NonNull
    private PageContext context;
//
//    @NonNull
//    private JButton button;

    @NonNull
    private JTextField textField;

    @NonNull
    private int chooseFileMode;

    @Override
    public void actionPerformed(ActionEvent e) {
//        System.out.println(e.getSource());
        String dialogTitle = "打开";
        if (chooseFileMode == 0) {
            dialogTitle = "请选择文件";
            String curSelectedFile = textField.getText();
            File currentDirectory = new File(curSelectedFile);
            if (currentDirectory.getParentFile().exists() && currentDirectory.getParentFile().isDirectory()) {
                context.getFileChooser().setCurrentDirectory(currentDirectory.getParentFile());
            } else {
                context.getFileChooser().setCurrentDirectory(FileUtil.getUserDirectory());
            }
        } else if (chooseFileMode == 1) {
            dialogTitle = "请选择文件夹";
            String curSelectedDir = textField.getText();
            File currentDirectory = new File(curSelectedDir);
            if (currentDirectory.exists() && currentDirectory.isDirectory()) {
                context.getFileChooser().setCurrentDirectory(currentDirectory);
            } else {
                context.getFileChooser().setCurrentDirectory(FileUtil.getUserDirectory());
            }
        }
        context.getFileChooser().setFileSelectionMode(chooseFileMode);// 设定只能选择到文件夹
        context.getFileChooser().setDialogTitle(dialogTitle);
        int state = context.getFileChooser().showOpenDialog(context.getTopWindow());// 此句是打开文件选择器界面的触发语句
        if (state != 1) {
            File selectedFile = context.getFileChooser().getSelectedFile();// selectedFile 为选择到的目录
            String selectedFilePath = FileUtil.getCanonicalPath(selectedFile);
            textField.setText(selectedFilePath);
            String configName = textField.getName();
            context.setConfig(configName, selectedFilePath);
            if (Constants.Configure.Keys.DIRECTORY_DATA.equals(configName)) {
                File uploadDir = new File(selectedFilePath, Constants.Configure.Directory.UPLOAD);
                context.setConfig(Constants.Configure.Keys.DIRECTORY_DATA_UPLOAD, FileUtil.getCanonicalPath(uploadDir));
                File uploadOfflineV1Dir = new File(uploadDir, Constants.Configure.Directory.STANDALONE_V1);
                context.setConfig(Constants.Configure.Keys.DIRECTORY_DATA_UPLOAD_OFFLINE_V1, FileUtil.getCanonicalPath(uploadOfflineV1Dir));
                File uploadOfflineV3Dir = new File(uploadDir, Constants.Configure.Directory.STANDALONE_V3);
                context.setConfig(Constants.Configure.Keys.DIRECTORY_DATA_UPLOAD_OFFLINE_V3, FileUtil.getCanonicalPath(uploadOfflineV3Dir));

                File mediaDir = new File(selectedFilePath, Constants.Configure.Directory.MEDIA);
                context.setConfig(Constants.Configure.Keys.DIRECTORY_DATA_MEDIA, FileUtil.getCanonicalPath(mediaDir));
            }
        }
    }
}
