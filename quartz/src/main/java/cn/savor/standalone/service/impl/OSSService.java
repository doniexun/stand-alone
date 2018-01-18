/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : java
 * @Package : cn.savor.quartz.oss.service.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 14:21
 */
package cn.savor.standalone.service.impl;

import cn.savor.aliyun.oss.IOSSClient;
import cn.savor.standalone.model.HotelInfo;
import cn.savor.standalone.service.IOSSService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Setter;
import net.lizhaoweb.common.util.base.HttpClientSimpleUtil;
import net.lizhaoweb.common.util.base.JsonUtil;
import net.lizhaoweb.common.util.base.StringUtil;
import net.lizhaoweb.spring.mvc.model.DataDeliveryWrapper;
import net.lizhaoweb.spring.mvc.model.StatusCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月23日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class OSSService implements IOSSService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Setter
    private String apiURL;
    @Setter
    private String mediaType;
    @Setter
    private String bucketName;
    @Setter
    private String dir;
    @Setter
    private IOSSClient ossClient;
    @Override
    public void createHotelDir() {
        //1.查询所有单机酒楼信息
        if(StringUtil.isBlank(apiURL)){
            throw new RuntimeException("请求酒店信息地址为空");
        }
        if(StringUtil.isBlank(mediaType)){
            throw new RuntimeException("资源类型为空");
        }
        String json=HttpClientSimpleUtil.get(apiURL);
        TypeReference<DataDeliveryWrapper<List<HotelInfo>>> typeReference = new TypeReference<DataDeliveryWrapper<List<HotelInfo>>>() {
        };
        try {
            DataDeliveryWrapper<List<HotelInfo>> dataDeliveryWrapper = JsonUtil.getInstance().readValue(json, typeReference);
            if (StatusCode.fromCode(dataDeliveryWrapper.getCode()) == StatusCode.SUCCESS) {
                List<HotelInfo> hotelInfos = dataDeliveryWrapper.getData();
                if(hotelInfos==null || hotelInfos.size()==0){
                    return;
                }
                //2.创建各类型目录
                String[] types=StringUtil.split(mediaType,",");
                String path=String.format("%s/%s",Thread.currentThread().getContextClassLoader().getResource("").getPath(),"init/1.txt");
                for(String type:types){
                    for(HotelInfo hotelInfo:hotelInfos){
                        if(StringUtil.isBlank(hotelInfo.getMenuName())){
                            continue;
                        }
                        String dirVal=String.format(dir,hotelInfo.getName(),hotelInfo.getMenuName(),type);
                        ossClient.putObject(bucketName,dirVal,path);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
