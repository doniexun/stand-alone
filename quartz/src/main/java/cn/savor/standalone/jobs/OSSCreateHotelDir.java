package cn.savor.standalone.jobs;

import cn.savor.standalone.service.IOSSService;
import lombok.Setter;

/**
 *@Author 刘飞飞
 *@Date 2017/10/30 11:25
 */
public class OSSCreateHotelDir {

    @Setter
    private IOSSService ossService;

    public void createHotelDir(){
         try {
             ossService.createHotelDir();
         }catch (Exception e){
             e.printStackTrace();
         }
    }
}
