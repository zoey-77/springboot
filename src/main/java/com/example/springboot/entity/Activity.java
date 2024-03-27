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
 * 社团活动表
 * </p>
 *
 * @author 
 * @since 
 */
@Getter
@Setter
@ApiModel(value = "Activity对象", description = "社团活动表")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @Alias("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("活动标题")
    @Alias("活动标题")
    private String name;

    @ApiModelProperty("活动内容")
    @Alias("活动内容")
    private String content;

    @ApiModelProperty("宣传图片")
    @Alias("宣传图片")
    private String img;

    @ApiModelProperty("发布时间")
    @Alias("发布时间")
    private String time;


}
