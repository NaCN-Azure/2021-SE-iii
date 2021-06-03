package com.heap.userservice.controller;


import com.heap.commonutils.JwtUtils;
import com.heap.commonutils.Result;
import com.heap.servicebase.exceptionhandler.COINException;
import com.heap.userservice.entity.User;
import com.heap.userservice.entity.vo.LoginVo;
import com.heap.userservice.entity.vo.RegisterVO;
import com.heap.userservice.entity.vo.UserInfoVO;
import com.heap.userservice.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 车一晗
 * @since 2021-05-07
 */
@RestController
@RequestMapping("/userservice/users")
@CrossOrigin
public class UsersController {

    @Autowired
    private UsersService usersService;

    //登录
    @PostMapping("login")
    public Result loginUser(@RequestBody LoginVo loginVo) {
        //member对象封装手机号和密码

        //返回token值，使用jwt生成
        String[] res = usersService.login(loginVo);

        return Result.ok().data("token", res[0]).data("id", res[1]);
    }

    //注册
    @PostMapping("register")
    public Result registerUser(@RequestBody RegisterVO registerVO) {
        usersService.register(registerVO);
        return Result.ok();
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public Result getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法，根据request对象获取头信息，返回用户id
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库，根据用户id获取用户信息
        User user = usersService.getById(userId);

        return Result.ok().data("userInfo", user);
    }

    //更新用户信息
    @PostMapping("updateUserInfo")
    public Result updateUserInfo(UserInfoVO userInfoVO) {
        String res = usersService.updateInfo(userInfoVO);
        if(res.equals("duplicateMobile")) {
            throw new COINException(201, "该手机号已存在");
        } else if(res.equals("error")) {
            throw new COINException(201, "修改失败");
        }

        return Result.ok();
    }

    //修改用户密码
    @PostMapping("updateUserPwd/{oldPwd}/{newPwd}")
    public Result updateUserPwd(@PathVariable String oldPwd, @PathVariable String newPwd, HttpServletRequest request) {
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        usersService.updatePassword(userId, oldPwd, newPwd);
        return Result.ok();
    }

    //重置用户密码
    @PostMapping("resetPwd/{mobile}/{password}")
    public Result resetPwd(@PathVariable String mobile, @PathVariable String password) {
        usersService.resetPassword(mobile, password);
        return Result.ok();
    }

}

