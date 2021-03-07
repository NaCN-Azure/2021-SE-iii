package com.heap.coinservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
 * @since 2021-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Entity对象", description="")
public class Entity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    private String name;

    private String bgColor;

    private Integer shape;


}
