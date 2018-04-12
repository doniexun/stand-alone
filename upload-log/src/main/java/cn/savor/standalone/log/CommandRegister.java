/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 15:33
 */
package cn.savor.standalone.log;

import cn.savor.standalone.log.createfile.CommandCreateBoxFile;
import cn.savor.standalone.log.download.CommandDownload;
import cn.savor.standalone.log.file.copy.CommandCopy;
import cn.savor.standalone.log.upload.CommandUpload;

/**
 * <h1>注册器 -  命令</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月09日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class CommandRegister {

    /**
     * 注册
     */
    public static void register() {
        CommandFactory.putCommand("copy", new CommandCopy());
        CommandFactory.putCommand("upload", new CommandUpload());
        CommandFactory.putCommand("download", new CommandDownload());
//        CommandFactory.putCommand("decompress", new CommandUpload());
        CommandFactory.putCommand("createBoxFile", new CommandCreateBoxFile());
    }
}
