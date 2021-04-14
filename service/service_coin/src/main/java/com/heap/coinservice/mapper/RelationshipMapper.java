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

    /**
     *
     * 更新关系信息
     * @param id name
     * @return
     */
    @Query("match (n)<-[r]-(m) where id(r)={0} set r.name={1} return r")
    Relationship updateLink(@Param("id") Long id, @Param("name") String name);

    /**
     *
     * 返回域内所有关系（不包含孤立节点）
     * @param domainId
     * @return
     */
    @Query("MATCH (n{domainId:{0}})-[r]->(m{domainId:{0}}) RETURN n,m,r")
    List<Relationship> getLinkByDomainId(@Param("domainId") int domainId);

    /**
     * 返回关系个数
     * @param domainId
     * @return
     */
    @Query("MATCH (n{domainId:{0}})-[r]->(m{domainId:{0}}) RETURN COUNT(r)")
    int countAllLink(@Param("domainId") int domainId);

    /**
     * 根据起始节点查找关系
     * @param fromId
     * @param toId
     * @return
     */
    @Query("MATCH (n)-[r]-(m) WHERE id(n) = {0} and id(m) = {1} RETURN r")
    Relationship findByToNodes(@Param("fromId") Long fromId,@Param("toId") Long toId);

}
