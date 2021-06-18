package com.heap.userservice.service;

import com.heap.userservice.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heap.userservice.entity.vo.LoginVo;
import com.heap.userservice.entity.vo.RegisterVO;
import com.heap.userservice.entity.vo.UserInfoAdminVO;
import com.heap.userservice.entity.vo.UserInfoVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 车一晗
 * @since 2021-05-07
 */
public interface UsersService extends IService<User> {

    /**
     * 登陆
     * @param loginVo
     * @return
     */
    String[] login(LoginVo loginVo);

    /**
     * 注册
     * @param registerVO
     */
    void register(RegisterVO registerVO);

    /**
     * 获取user令牌
     * @param user
     * @return
     */
    UserInfoVO getUserInfo(User user);

    /**
     * 更新用户信息
     * @param userInfoVO
     */
    void updateInfo(UserInfoVO userInfoVO);

    /**
     * 更新动态信息
     * @param userInfoVO
     */
    void updateAvatar(UserInfoVO userInfoVO);

    /**
     * 更改密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     */
    void updatePassword(String userId, String oldPwd, String newPwd);

    /**
     * 重置密码
     * @param mobile
     * @param password
     */
    void resetPassword(String mobile, String password);

    /**
     * 注销用户
     * @param id
     */
    void disableUser(String id);

    /**
     * 管理员修改信息
     * @param userInfoAdminVO
     */
    void updateUserInfoAdmin(UserInfoAdminVO userInfoAdminVO);

    /**
     * 新增用户
     * @param user
     */
    void addUser(User user);

    /**
     * 删除用户
     * @param id
     */
    void deleteUser(String id);

    /**
     * 设置会员
     * @param id
     * @param days
     */
    void setVipUser(String id, int days);
}
