package com.heap.coinservice.mapper;

import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.entity.Relationship;
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
 * @author 纳思彧
 * @since 2021-06-01
 */
@Repository
@Mapper
public interface QuestionMapper extends Neo4jRepository<Entity,Long> {

    /**
     * 模板0问题：节点及其周边
     * @param domainId
     * @param nodeName
     * @return
     */
    @Query("MATCH (n{domainId:{0}})-[r]-(m{domainId:{0}}) where n.name={1} RETURN m.name")
//    @Query("MATCH (n)-[r]-(m) where n.name={1} RETURN n,m,r")
    List<String> getNodesNear(@Param("domainId") int domainId,@Param("nodeName") String nodeName);

    /**
     * 模板1问题：节点信息
     * @param name
     * @param domainId
     * @return
     */
    @Query("MATCH (n) where n.name = {0} and n.domainId = {1} return n")
    Entity getNodeDesciption(@Param("name") String name, @Param("domainId") int domainId);

    /**
     * 模板2问题：两节点关系
     * @param domainId
     * @param name1
     * @param name2
     * @return
     */
    @Query("MATCH (n{domainId:{0}})-[r]-(m{domainId:{0}}) where n.name = {1} and m.name = {2} return r.name")
    String getLinkWithTwoNodes(@Param("domainId") int domainId,@Param("name1") String name1, @Param("name2") String name2);

    /**
     * 模板3问题：一节点一关系
     * @param domainId
     * @param nodeName
     * @param linkName
     * @return
     */
    @Query("MATCH (n{domainId:{0}})-[r]-(m{domainId:{0}}) where n.name={1} and r.name = {2} RETURN m.name")
    List<String> getTargetNodes(@Param("domainId") int domainId,@Param("nodeName") String nodeName,@Param("linkName") String linkName);

    /**
     * 模板4问题：类型节点
     * @param type
     * @param domainId
     * @return
     */
    @Query("MATCH (n) where n.type = {0} and n.domainId = {1} return n.name")
    List<String> getTypeNodes(@Param("type") String type, @Param("domainId") int domainId);

}
