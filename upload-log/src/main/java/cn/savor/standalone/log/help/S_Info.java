/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : java
 * @Package : cn.savor.standalone.log.help
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 15:48
 */
package cn.savor.standalone.log.help;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年10月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class S_Info {

    // 帮助说明
    public static void helpDescription() {
        System.out.println("Usage: upload <ARGUMENT>...");
        System.out.println("Command upload argument:");
        System.out.println("    fromDir=DIR      Local directory for reading files");
        System.out.println("    tempDir=DIR      Local storage temporary directory");
        System.out.println("    bucketName=WORD  The OSS bucket name for the file to upload");
        System.out.println("    keyPrefix=WORD   The prefix of the file in OSS");
    }
}
