/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log.gui
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 11:37
 */
package cn.savor.standalone.log.gui;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @version 1.0.0.0.1
 * @notes Created on 2018年04月12日<br>
 *        Revision of last commit:$Revision$<br>
 *        Author of last commit:$Author$<br>
 *        Date of last commit:$Date$<br>
 *
 */
@RequiredArgsConstructor
public class PageContext {

    @NonNull
    @Getter
    private ApplicationContext applicationContext;
}
