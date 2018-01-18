package cn.savor.quartz;

import org.junit.Rule;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *@Author 刘飞飞
 *@Date 2017/8/30 23:13
 */
public class MySpringJUnit4ClassRunner extends SpringJUnit4ClassRunner {
    @Rule
    public final EnvironmentVariables environmentVariables
            = new EnvironmentVariables();

    public MySpringJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
        environmentVariables.set("RUNTIME_ENVIRONMENT", "development");
    }

}
