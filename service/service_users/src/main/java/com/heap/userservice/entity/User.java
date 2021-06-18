package com.heap.userservice.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author 车一晗
 * @since 2021-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    //ID_WORKER是MyBatis-plus的主键生成策略，生成一个全局唯一id
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "等级")
    private Integer level;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "是否注销 1（true）已注销，0（false）未注销")
    private Boolean isDisabled;

    @ApiModelProperty(value = "是否为会员 1（true）是，0（false）不是")
    private Boolean isVip;

    @ApiModelProperty(value = "会员到期时间")
    private Date vipEndTime;

    @ApiModelProperty(value = "用户签名")
    private String sign;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;


}
