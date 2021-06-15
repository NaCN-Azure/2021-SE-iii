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
 * @author 车一晗
 * @since 2021-03-07
 */
@Repository
@Mapper
public interface QuestionMapper extends Neo4jRepository<Entity,Long> {

    @Query("MATCH (n{domainId:{0}})-[r]-(m{domainId:{0}}) where n.name={1} RETURN m.name")
//    @Query("MATCH (n)-[r]-(m) where n.name={1} RETURN n,m,r")
    List<String> getNodesNear(@Param("domainId") int domainId,@Param("nodeName") String nodeName);

    @Query("MATCH (n) where n.name = {0} and n.domainId = {1} return n")
    Entity getNodeDesciption(@Param("name") String name, @Param("domainId") int domainId);

    @Query("MATCH (n{domainId:{0}})-[r]-(m{domainId:{0}}) where n.name = {1} and m.name = {2} return r.name")
    String getLinkWithTwoNodes(@Param("domainId") int domainId,@Param("name1") String name1, @Param("name2") String name2);

    @Query("MATCH (n{domainId:{0}})-[r]-(m{domainId:{0}}) where n.name={1} and r.name = {2} RETURN m.name")
    List<String> getTargetNodes(@Param("domainId") int domainId,@Param("nodeName") String nodeName,@Param("linkName") String linkName);

    @Query("MATCH (n) where n.type = {0} and n.domainId = {1} return n.name")
    List<String> getTypeNodes(@Param("type") String type, @Param("domainId") int domainId);

}
