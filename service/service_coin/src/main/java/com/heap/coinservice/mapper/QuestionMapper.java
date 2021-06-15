package com.heap.coinservice.mapper;

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
public interface QuestionMapper extends Neo4jRepository<Relationship,Long> {

    @Query("MATCH (n{domainId:{0}})-[r]-(m{domainId:{0}}) where n.name={1} RETURN n,m,r")
//    @Query("MATCH (n)-[r]-(m) where n.name={1} RETURN n,m,r")
    List<Relationship> getNodesNear(@Param("domainId") int domainId,@Param("nodeName") String nodeName);

}
