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
    @Query("MATCH (n) WHERE id(n) = {0} SET n.name = {1},n.description = {2} RETURN n")
    Entity updateNode(@Param("id") Long id, @Param("name") String name, @Param("description") String description);

    /**
     *
     * 返回域内没有任何关系的孤立节点
     * @param domainId
     * @return
     */
    @Query("match (n{domainId:{0}}) where not (n)–[]-() return n")
    List<Entity> getUnLinkNodes(@Param("domainId") int domainId);

    /**
     *
     * 删除节点及其附属节点
     * @param id
     */
    @Query("MATCH (n)-[r]-() WHERE id(n) = {0} DELETE n,r")
    void deleteNodeWithLink(@Param("id") Long id);

    /**
     * 按名称查询节点
     * @param name
     * @param domainId
     * @return
     */
    @Query("MATCH (n) where n.name = {0} and n.domainId = {1} return n")
    Entity findByName(@Param("name") String name, @Param("domainId") int domainId);

    /**
     * 统计节点个数
     * @param domainId
     * @return
     */
    @Query("MATCH (n{domainId:{0}}) return COUNT(n)")
    int countAllEntity(@Param("domainId") int domainId);

    /**
     * 改变位置
     * @param id
     * @param x
     * @param y
     * @return
     */
    @Query("MATCH (n) WHERE id(n) = {0} SET n.x = {1},n.y = {2} RETURN n")
    Entity updateXY(Long id, double x, double y);
}
