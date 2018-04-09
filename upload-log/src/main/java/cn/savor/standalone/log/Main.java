/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : java
 * @Package : cn.savor.standalone.log.upload
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:50
 */
package cn.savor.standalone.log;

import cn.savor.standalone.log.help.S_Info;
import net.lizhaoweb.common.util.argument.ArgumentFactory;

/**
 * 主类
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年09月29日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class Main {

    public static void main(String[] args) {
        try {
            CommandRegister.register();
            if (args == null) {
                S_Info.helpDescription();
                System.exit(Status.SUCCESS);
            }
            if (args.length < 1) {
                S_Info.helpDescription();
                System.exit(Status.SUCCESS);
            }

            String com = args[0];
            if ("--help".equalsIgnoreCase(com)) {
                S_Info.helpDescription();
                System.exit(Status.SUCCESS);
            }

            ICommand command = CommandFactory.getCommand(com);
            if (command == null) {
                String message = String.format("Command '%s' not found", com);
                System.out.println(message);
                System.exit(Status.COM_NOT_FOUND);
            }
            ArgumentFactory.analysisArgument(args);
            command.execute();

//            Command command = Command.fromName(com);
//            if (Command.UNKNOWN == command) {
//                String message = String.format("Command '%s' not found", command);
//                System.out.println(message);
//                System.exit(Status.COM_NOT_FOUND);
//            }
//            if (Command.UPLOAD == command) {
//                ArgumentFactory.analysisArgument(args);
//                LogFileUploader.execute();// 上传日志文件
//            }
//            if (Command.DOWNLOAD == command) {
//                ArgumentFactory.analysisArgument(args);
//                LogFileDownloader.execute();//下载文件
//            }
//            if (Command.CREATEFILE == command) {
//                ArgumentFactory.analysisArgument(args);
//                CreateBoxFile.execute();//制作U盘
//            }


            System.exit(Status.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(Status.EXCEPTION);
        }
    }
}
