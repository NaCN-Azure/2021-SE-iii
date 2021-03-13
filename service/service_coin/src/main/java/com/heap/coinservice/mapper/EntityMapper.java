package com.heap.coinservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heap.coinservice.entity.Entity;
import org.apache.ibatis.annotations.Mapper;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 车一晗
 * @since 2021-03-07
 */
@Repository
@Mapper
public interface EntityMapper extends Neo4jRepository<Entity,Long> {

}
