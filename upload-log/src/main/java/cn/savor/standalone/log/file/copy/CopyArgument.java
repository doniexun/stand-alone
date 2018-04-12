/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log.file.copy
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:46
 */
package cn.savor.standalone.log.file.copy;

import net.lizhaoweb.common.util.argument.model.AbstractArgument;
import net.lizhaoweb.common.util.argument.model.IArgument;

/**
 * <h1>参数名 - 复制</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月12日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class CopyArgument extends AbstractArgument {

    public static final IArgument sourceDirectory = new CopyArgument("srcDir", null, null);
    public static final IArgument targetDirectory = new CopyArgument("tarDir", null, null);
    public static final IArgument regex = new CopyArgument("regex", null, null);

    public CopyArgument(String name, String nullValue, String[] nullArray) {
        super(name, nullValue, nullArray);
    }
}
