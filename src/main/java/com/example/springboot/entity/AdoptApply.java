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
@TableName("adopt_apply")
@ApiModel(value = "activityApply对象", description = "申请审批表")
public class AdoptApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @Alias("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("相关领养")
    @Alias("相关领养")
    private String name;

    @ApiModelProperty("申请说明")
    @Alias("申请说明")
    private String description;

    @ApiModelProperty("用户")
    @Alias("用户")
    private String user;

    @ApiModelProperty("领养ID")
    @Alias("领养ID")
    private Integer adoptId;

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
