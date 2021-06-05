package com.heap.userservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInfoAdminVO {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "用户名")
    private String nickname;

    @ApiModelProperty(value = "等级")
    private Integer level;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "个性签名")
    private String sign;
}
