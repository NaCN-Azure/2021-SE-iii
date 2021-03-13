package com.heap.coinservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * <p>
 *
 * </p>
 *
 * @author 车一晗
 * @since 2021-03-07
 */
@NodeEntity
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class Entity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String bgColor;

    private Integer shape;

    private int domainId;


}
