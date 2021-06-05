package com.heap.coinservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface DomainMapper extends BaseMapper<Domain> {

    /**
     * 新增域
     * @param domain
     */
    @Insert("insert into domain(name,user_id) values (#{name},#{user_id})")
    void createDomain(Domain domain);

    /**
     * 删除域
     * @param id
     */
    @Delete("delete from domain where id = #{id}")
    void deleteDomain(@Param("id") int id);

    /**
     * 更新域
     * @param name
     * @param id
     */
    @Update("update domain set name = #{name} where id =#{id}")
    void updateDomain(@Param("name") String name, @Param("id") int id);

    /**
     * 返回当前域
     * @param id
     * @return
     */
    @Select("select * from domain where id = #{id}")
    Domain selectDomain(@Param("id") int id);

    /**
     * 返回所有域
     * @return
     */
    @Select("select * from domain where user_id = #{user_id} ")
    List<Domain> selectAllDomain(@Param("user_id") String userId);

    /**
     * 返回最近新增域的id
     * @return
     */
    @Select("select last_insert_id()")
    int lastInsertId();

    /**
     * 根据名字查找域名个数
     * @param name
     * @return
     */
    @Select("select * from domain where name = #{name} and user_id = #{user_id}")
    Domain selectDomainByName(@Param("name") String name,@Param("user_id") String user_id);

}
