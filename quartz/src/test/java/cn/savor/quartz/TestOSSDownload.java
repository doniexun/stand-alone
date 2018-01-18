package cn.savor.quartz;

import cn.savor.standalone.jobs.OSSCreateHotelDir;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 *@Author 刘飞飞
 *@Date 2017/8/30 22:29
 */
@RunWith(MySpringJUnit4ClassRunner.class)//使用junit4进行测试  
@ContextConfiguration("classpath*:/schema/spring/spring-*.xml")
public class TestOSSDownload {

    @Autowired
    private OSSCreateHotelDir ossCreateHotelDir;

    @Test
    public void createDir(){
        ossCreateHotelDir.createHotelDir();
    }

   @Before
   public void setEnv(){
       System.out.println(System.getenv("RUNTIME_ENVIRONMENT"));
   }

}
