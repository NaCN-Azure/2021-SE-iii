package com.heap.coinservice.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "图谱查询对象", description = "图谱查询对象封装")
@Data
public class DomainQuery {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "图谱名称,模糊查询")
    private String name;

    @ApiModelProperty(value = "所属用户id")
    private String userId;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
