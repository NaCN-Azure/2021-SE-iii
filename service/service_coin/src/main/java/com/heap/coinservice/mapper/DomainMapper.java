package com.heap.coinservice.mapper;

import com.heap.coinservice.entity.Domain;
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
public interface DomainMapper  {

    @Insert("insert into domain(name) values (#{name})")
    void createDomain(Domain domain);

    @Delete("delete from domain where id = #{id}")
    void deleteDomain(@Param("id") int id);

    @Update("update domain set name = #{name} where id =#{id}")
    void updateDomain(@Param("name") String name,@Param("id") int id);

    @Select("select * from domain where id = #{id}")
    Domain selectDomain(@Param("id") int id);

    @Select("select * from domain")
    List<Domain> selectAllDomain();

    @Select("select last_insert_id()")
    int lastInsertId();

}
