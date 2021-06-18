package com.heap.coinservice.mapper;

import com.heap.coinservice.entity.Entity;
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
 * @since 2021-03-08
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
     */
    @Query("MATCH (n) WHERE id(n) = {0} SET n.name = {1},n.description = {2}, n.r = {3}, n.shape = {4} ,n.fontSize = {5} RETURN n")
    void updateNode(@Param("id") Long id, @Param("name") String name, @Param("description") String description,
                      @Param("r") double r,@Param("shape") int shape,@Param("fontSize") int fontSize);

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
     * 改变位置(力导图)
     * @param id
     * @param X
     * @param Y
     */
    @Query("MATCH (n) WHERE id(n) = {0} SET n.X = {1},n.Y = {2} RETURN n")
    void updateForceXY(@Param("id") Long id, @Param("X") double X, @Param("Y") double Y);

    /**
     * 改变位置(排版图)
     * @param id
     * @param composeX
     * @param composeY
     */
    @Query("MATCH (n) WHERE id(n) = {0} SET n.composeX = {1},n.composeY = {2} RETURN n")
    void updateComposeXY(@Param("id") Long id, @Param("composeX") double composeX, @Param("composeY") double composeY);

    /**
     *
     * 统计一个类型的域节点个数
     * @param domainId
     * @param type
     * @return
     */
    @Query("MATCH (n{domainId:{0}}) where n.type = {1} return COUNT(n)")
    int countEntitiesByType(@Param("domainId") int domainId,@Param("type") String type);

    /**
     * 更新所有节点颜色
     * @param type
     * @param color
     */
    @Query("MATCH (n{type:{1}}) where n.domainId = {0} set n.bgColor = {2}")
    void updateAllColors(@Param("domainId") int domainId,@Param("type") String type,@Param("color") String color);

    /**
     * 按类型得到节点
     * @param domainId
     * @param type
     * @return
     */
    @Query("MATCH (n) where n.domainId = {0} and n.type = {1} return n")
    List<Entity> getNodeByType(@Param("domainId") int domainId,@Param("type") String type);

    /**
     * 更改类型及颜色
     * @param id
     * @param type
     * @param bgColor
     */
    @Query("MATCH (n) where id(n) = {0} set n.type = {1},n.bgColor= {2} return n")
    void updateType(@Param("id") Long id,@Param("type") String type,@Param("bgColor") String bgColor);

}
