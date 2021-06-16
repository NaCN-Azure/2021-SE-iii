package com.heap.coinservice.mapper;

import org.apache.ibatis.annotations.*;
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
@Mapper
@Repository
public interface TypeMapper {

    @Insert("insert into nodetype(color,type,domain_id) values (#{color},#{type},#{domainId})")
    void insertType(@Param("domainId") int domainId,@Param("color") String color, @Param("type") String nodeType);

    @Delete("delete from nodetype where type = #{type} and domain_id =#{domainId}")
    void deleteType(@Param("domainId") int domainId,@Param("type") String type);

    @Select("select color from nodetype where type = #{type} and domain_id =#{domainId}")
    String searchColorByType(@Param("domainId") int domainId,@Param("type") String nodeType);

    @Select("select type from nodetype where domain_id =#{domainId}")
    List<String> searchAll(@Param("domainId") int domainId);

    @Select("select color from nodetype where domain_id =#{domainId}")
    List<String> searchColor(@Param("domainId") int domainId);

    @Select("select color from colors where color not in( select color from nodetype where nodetype.domain_id =#{domainId}) limit 1")
    String getNewColor(@Param("domainId") int domainId);

}
