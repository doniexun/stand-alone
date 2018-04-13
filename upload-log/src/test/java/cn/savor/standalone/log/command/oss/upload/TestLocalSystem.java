/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : java
 * @Package : cn.savor.standalone.log.upload
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:37
 */
package cn.savor.standalone.log.command.oss.upload;

import cn.savor.standalone.log.command.createfile.CommandCreateBoxFile;
import cn.savor.standalone.log.command.oss.upload.bean.LocalFile;
import cn.savor.standalone.log.command.oss.upload.service.LocalSystem;
import net.lizhaoweb.common.util.base.Constant;
import net.lizhaoweb.common.util.base.FileUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年09月29日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestLocalSystem {

    @Test
    public void loopDirectory() {
        String directory = "h:\\";
        LocalFile localFile = LocalSystem.loopDirectory(directory);
        for (File d : localFile.getDirectoryList()) {
            System.out.println(d);
        }
        System.out.println("-----------------------------");
        for (File f : localFile.getFileList()) {
            System.out.println(f);
        }
    }

    @Test
    public void create() throws IOException {
        new CommandCreateBoxFile().execute();
    }

    @Test
    public void listTheLetter() {
//        File[] roots = File.listRoots();
//        for (File file : roots) {
//            System.out.println(file.getPath() + "信息如下:");
//            System.out.println("        空闲未使用 = " + file.getFreeSpace() / 1024 / 1024 / 1024 + "G");//空闲空间
//            System.out.println("        已经使用 = " + (file.getTotalSpace() - file.getFreeSpace()) / 1024 / 1024 / 1024 + "G");//可用空间
//            System.out.println("        总容量 = " + file.getTotalSpace() / 1024 / 1024 / 1024 + "G");//总空间
//            System.out.println();
//        }

        for (File file : FileUtil.listRootsForWindows(Constant.DriveType.Windows.REMOVABLE)) {
            System.out.println(file.getPath());
        }
    }
}
