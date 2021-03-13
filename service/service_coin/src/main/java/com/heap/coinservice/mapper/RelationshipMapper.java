package com.heap.coinservice.mapper;

import com.heap.coinservice.entity.Relationship;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
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
public interface RelationshipMapper extends Neo4jRepository<Relationship,Long> {

}
