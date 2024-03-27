package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import cn.hutool.core.annotation.Alias;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 
 */
@Getter
@Setter
@ApiModel(value = "Cat对象", description = "")
public class Cat implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @Alias("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("昵称")
    @Alias("昵称")
    private String name;

    @ApiModelProperty("毛色")
    @Alias("毛色")
    private String color;

    @ApiModelProperty("状态")
    @Alias("状态")
    private String state;

    @ApiModelProperty("性格")
    @Alias("性格")
    private String characters;

    @ApiModelProperty("照片")
    @Alias("照片")
    private String img;

    @ApiModelProperty("介绍")
    @Alias("介绍")
    private String content;

    @ApiModelProperty("添加时间")
    @Alias("添加时间")
    private String time;


}
