package com.heap.userservice.mapper;

import com.heap.userservice.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 车一晗
 * @since 2021-05-07
 */
@Mapper
@Repository
public interface UsersMapper extends BaseMapper<User> {

}
