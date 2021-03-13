package com.heap.coinservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @author 车一晗
 * @since 2021-03-07
 */
@RelationshipEntity(type="supply")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Relationship", description="")
public class Relationship implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer type;

    private Long fromId;

    private Long toId;

    @StartNode
    private Entity startEntity;

    @EndNode
    private Entity endEntity;


}
