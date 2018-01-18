package cn.savor.standalone.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *@Author 刘飞飞
 *@Date 2017/10/30 12:35
 */
@Data
public class HotelInfo implements Serializable {
    /**
     * 酒楼id
     */
    @JsonProperty(value = "id")
    private Long id;

    /**
     * 酒楼名称
     */
    @JsonProperty(value = "hotel_name")
    private String name;

    @JsonProperty(value = "menu_name")
    private String menuName;
}
