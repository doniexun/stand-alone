/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : stand-alone
 * @Package : cn.savor.standalone.log.upload
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 14:25
 */
package cn.savor.standalone.log.upload;

import cn.savor.standalone.log.Configure;
import cn.savor.standalone.log.FileLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月09日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestFileLoader {

    private FileLoader loader;

    @Before
    public void init() {
        loader = new FileLoader();
    }

    @Test
    public void loadProperties() {
        try {
            Configure configure = new Configure();
            Properties properties = loader.loadDataFile(configure);
            System.out.println(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
