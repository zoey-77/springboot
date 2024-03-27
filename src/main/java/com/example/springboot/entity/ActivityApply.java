package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import cn.hutool.core.annotation.Alias;

/**
 * <p>
 * 申请审批表
 * </p>
 *
 * @author 
 * @since 
 */
@Getter
@Setter
@TableName("activity_apply")
@ApiModel(value = "activityApply对象", description = "申请审批表")
public class ActivityApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @Alias("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("活动")
    @Alias("活动")
    private String name;

    @ApiModelProperty("申请说明")
    @Alias("申请说明")
    private String description;

    @ApiModelProperty("用户")
    @Alias("用户")
    private String user;

    @ApiModelProperty("活动ID")
    @Alias("活动ID")
    private Integer activityId;

    @ApiModelProperty("审核人姓名")
    @Alias("审核人姓名")
    private String reviewer;

    @ApiModelProperty("审核状态")
    @Alias("审核状态")
    private String state;

    @ApiModelProperty("审核说明")
    @Alias("审核说明")
    private String reason;


}
