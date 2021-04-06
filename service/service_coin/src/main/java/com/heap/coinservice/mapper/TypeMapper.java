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

    @Insert("insert into nodetype(color,type) values (#{color},#{type})")
    void insertType(@Param("color") String color, @Param("type") String nodeType);

    @Update("update nodetype set color = #{color} where type =#{type}")
    void updateColor(@Param("type") String type,@Param("color") String color);

    @Delete("delete from nodetype where type = #{type}")
    void deleteType(@Param("type") String type);

    @Select("select color from nodetype where type = #{type}")
    String searchColorByType(@Param("type") String nodeType);

    @Select("select type from nodetype")
    List<String> searchAll();

}
