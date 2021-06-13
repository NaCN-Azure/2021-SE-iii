package com.heap.coinservice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class Domain implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "图谱id")
    private int id;

    @ApiModelProperty(value = "图谱名称")
    private String name;

    @ApiModelProperty(value = "所属用户id")
    private String user_id;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除，0（false）未删除")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
}
