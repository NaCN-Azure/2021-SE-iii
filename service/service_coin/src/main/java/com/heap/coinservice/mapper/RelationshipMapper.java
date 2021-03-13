package com.heap.coinservice.mapper;

import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.entity.Relationship;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Query("match (n)<-[r]-(m) where id(r)={0} set r.name={1} return r")
    Relationship updateLink(@Param("id") Long id,@Param("name") String name);

    @Query("match [r{domainId:{0}}] return r")
    List<Relationship> getlinkbydomainid(@Param("domainId") Long domainId);
}
