package com.heap.userservice.service;

import com.heap.userservice.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heap.userservice.entity.vo.LoginVo;
import com.heap.userservice.entity.vo.RegisterVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 车一晗
 * @since 2021-05-07
 */
public interface UsersService extends IService<User> {

    String login(LoginVo loginVo);

    void register(RegisterVO registerVO);
}
