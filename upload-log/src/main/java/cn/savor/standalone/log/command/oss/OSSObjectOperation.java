/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log.command.oss
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 15:01
 */
package cn.savor.standalone.log.command.oss;

import cn.savor.standalone.log.command.AbstractCommand;
import lombok.NoArgsConstructor;

import java.io.OutputStream;

/**
 * <h1>抽象类 - OSS 对象操作</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@NoArgsConstructor
public abstract class OSSObjectOperation extends AbstractCommand {

    public OSSObjectOperation(OutputStream outputStream) {
        super(outputStream);
    }
}
