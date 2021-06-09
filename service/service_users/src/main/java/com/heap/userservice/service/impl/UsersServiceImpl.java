package com.heap.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heap.commonutils.JwtUtils;
import com.heap.commonutils.MD5;
import com.heap.servicebase.exceptionhandler.COINException;
import com.heap.userservice.entity.User;
import com.heap.userservice.entity.vo.LoginVo;
import com.heap.userservice.entity.vo.RegisterVO;
import com.heap.userservice.entity.vo.UserInfoAdminVO;
import com.heap.userservice.entity.vo.UserInfoVO;
import com.heap.userservice.mapper.UsersMapper;
import com.heap.userservice.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 车一晗
 * @since 2021-05-07
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, User> implements UsersService {

    @Override
    public String[] login(LoginVo loginVo) {
        //获取登录手机号和密码
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //上述两者有一个为空即不往下执行
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new COINException(201, "手机号或密码不能为空");
        }

        //判断手机号是否正确
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        User user = baseMapper.selectOne(wrapper);
        if(user == null) {
            throw new COINException(201, "该手机号不存在");
        }

        //判断该用户是否被封禁
        if(user.getIsDisabled()) {
            throw new COINException(201, "该用户封禁中");
        }

        //判断密码
        //因为存储到数据库的密码是加密的
        //所以需要把输入的密码进行加密，再和数据库中的密码进行比较
        //加密方式：MD5
        if(!MD5.encrypt(password).equals(user.getPassword())) {
            throw new COINException(201, "密码错误");
        }

        //判断用户是否被禁用
        if(user.getIsDisabled()) {
            throw new COINException(201, "该用户被禁用");
        }

        //执行到这里即登录成功

        //生成token字符串，使用jwt工具类
        String token = JwtUtils.getJwtToken(user.getId(), user.getNickname());
        String id = user.getId();
        String[] res = new String[2];
        res[0] = token;
        res[1] = id;

        return res;
    }

    @Override
    public void register(RegisterVO registerVO) {
        //获取注册数据
        String mobile = registerVO.getMobile();       //手机号
        String nickname = registerVO.getNickname();   //昵称
        String password = registerVO.getPassword();   //密码

        //非空判断
        if(StringUtils.isEmpty(mobile)) {
            throw new COINException(201, "手机号不能为空");
        }
        if(StringUtils.isEmpty(nickname)) {
            throw new COINException(201, "昵称不能为空");
        }
        if(StringUtils.isEmpty(password)) {
            throw new COINException(201, "密码不能为空");
        }

        //判断手机号是否已经注册
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        //这里不需要把数据查出来，只需要知道有没有，所以调用selectCount就行
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            throw new COINException(201, "该用户已存在");
        }

        //这时注册成功，将数据添加到数据库中
        User user = new User();
        user.setMobile(mobile);
        user.setNickname(nickname);
        user.setPassword(MD5.encrypt(password));
        user.setIsDisabled(false);
        user.setAvatar("https://heap-coin.oss-cn-beijing.aliyuncs.com/default.PNG");
        baseMapper.insert(user);

    }

    @Override
    public void updateInfo(UserInfoVO userInfoVO) {
        String id = userInfoVO.getId();
        String nickname = userInfoVO.getNickname();
        String sign = userInfoVO.getSign();

        //先查找到需要修改信息的用户
        User user = baseMapper.selectById(id);

        user.setNickname(nickname);
        user.setSign(sign);

        baseMapper.updateById(user);
    }

    @Override
    public void updateAvatar(UserInfoVO userInfoVO) {
        String id = userInfoVO.getId();
        String avatar = userInfoVO.getAvatar();
        User user = baseMapper.selectById(id);
        user.setAvatar(avatar);
        baseMapper.updateById(user);
    }

    @Override
    public void updatePassword(String userId, String oldPwd, String newPwd) {
        User user = baseMapper.selectById(userId);

        if(MD5.encrypt(oldPwd).equals(user.getPassword())) {
            //原密码正确
            user.setPassword(MD5.encrypt(newPwd));
            baseMapper.updateById(user);
        }
        else {
            throw new COINException(201, "原密码错误");
        }

    }

    @Override
    public void resetPassword(String mobile, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        User user = baseMapper.selectOne(wrapper);

        //存在该用户
        if(user != null) {
            user.setPassword(MD5.encrypt(password));
            baseMapper.updateById(user);
        }
        else {
            throw new COINException(201, "不存在该手机号");
        }
    }

    @Override
    public void disableUser(String id) {
        User user = baseMapper.selectById(id);
        Boolean isDisabled = user.getIsDisabled();
        isDisabled = !isDisabled;
        user.setIsDisabled(isDisabled);
        baseMapper.updateById(user);
    }

    @Override
    public void updateUserInfoAdmin(UserInfoAdminVO userInfoAdminVO) {
        String id = userInfoAdminVO.getId();
        User user = baseMapper.selectById(id);

        String mobile = userInfoAdminVO.getMobile();

        //看一下是否已经存在手机号
        if(!user.getMobile().equals(mobile)) {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("mobile", mobile);
            //这里不需要把数据查出来，只需要知道有没有，所以调用selectCount就行
            Integer count = baseMapper.selectCount(wrapper);
            if(count > 0) {
                throw new COINException(201, "该用户已存在");
            }
        }

        String nickname = userInfoAdminVO.getNickname();
        String avatar = userInfoAdminVO.getAvatar();
        Integer level = userInfoAdminVO.getLevel();
        String sign = userInfoAdminVO.getSign();

        user.setMobile(mobile);
        user.setNickname(nickname);
        user.setAvatar(avatar);
        user.setLevel(level);
        user.setSign(sign);

        baseMapper.updateById(user);
    }

    @Override
    public void addUser(User user) {
        String password = user.getPassword();
        user.setPassword(MD5.encrypt(password));
        this.save(user);
    }

    @Override
    public void deleteUser(String id) {
        baseMapper.deleteById(id);
    }


}
