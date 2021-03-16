package com.heap.coinservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heap.coinservice.entity.Entity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.neo4j.ogm.annotation.RelationshipEntity;
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
public interface EntityMapper extends Neo4jRepository<Entity,Long> {

    /**
     *
     * 返回域内的所有节点信息
     * @param domainId
     * @return
     */
    @Query("match(n{domainId:{0}}) return n")
    List<Entity> getNodeByDomainId(@Param("domainId") int domainId);

    /**
     *
     * 更新节点信息
     * @param id name bgColor
     * @return
     */
    @Query("MATCH (n) WHERE id(n) = {0} SET n.name = {1},n.bgColor = {2} RETURN n")
    Entity updateNode(@Param("id") Long id,@Param("name") String name,@Param("bgColor") String bgColor);

    /**
     *
     * 返回域内没有任何关系的孤立节点
     * @param domainId
     * @return
     */
    @Query("match (n{domainId:{0}}) where not (n)–[]-() return n")
    List<Entity> getUnLinkNodes(@Param("domainId") int domainId);
}
