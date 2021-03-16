package com.heap.coinservice.mapper;

import com.heap.coinservice.entity.Domain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
public interface DomainMapper  {

    Domain createDomain(@Param("domain") Domain domain);

    void deleteDomain(@Param("id") int id);

    Domain updateDomain(@Param("name") String name);

    Domain selectDomain(@Param("id") int id);

    List<Domain> selectAllDomain();

}
