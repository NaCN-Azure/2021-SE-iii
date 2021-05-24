package com.heap.userservice.controller;


import com.heap.commonutils.JwtUtils;
import com.heap.commonutils.Result;
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
        String token = usersService.login(loginVo);

        return Result.ok().data("token", token);
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
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库，根据用户id获取用户信息
        User user = usersService.getById(memberId);

        return Result.ok().data("userInfo", user);
    }

    //更新用户信息
    @PostMapping("updateUserInfo")
    public Result updateUserInfo(UserInfoVO userInfoVO) {
        String res = usersService.updateInfo(userInfoVO);
        if(res.equals("duplicateMobile")) {
            return Result.error().message("该手机号码已经存在");
        } else if(res.equals("error")) {
            return Result.error().message("修改失败");
        }

        return Result.ok();
    }

}

