package com.heap.userservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heap.commonutils.JwtUtils;
import com.heap.commonutils.Result;
import com.heap.servicebase.exceptionhandler.COINException;
import com.heap.userservice.entity.User;
import com.heap.userservice.entity.query.UserQuery;
import com.heap.userservice.entity.vo.LoginVo;
import com.heap.userservice.entity.vo.RegisterVO;
import com.heap.userservice.entity.vo.UserInfoVO;
import com.heap.userservice.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    //根据id获取用户信息
    @GetMapping("getUserInfo/{id}")
    public Result getUserInfo(@PathVariable String id) {
        User user = usersService.getById(id);
        return Result.ok().data("userInfo", user);
    }

    //更新用户信息
    @PostMapping("updateUserInfo/{id}/{nickname}/{sign}")
    public Result updateUserInfo(@PathVariable String id, @PathVariable String nickname, @PathVariable String sign) {
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setId(id);
        userInfoVO.setNickname(nickname);
        userInfoVO.setSign(sign);

        usersService.updateInfo(userInfoVO);

        return Result.ok();
    }

    //保存头像
    @PostMapping("updateAvatar/{id}/{avatar}")
    public Result updateAvatar(@PathVariable String id, @PathVariable String avatar) {
        usersService.updateAvatar(id, avatar);
        return Result.ok();
    }

    //修改用户密码
    @PostMapping("updateUserPwd/{id}/{oldPwd}/{newPwd}")
    public Result updateUserPwd(@PathVariable String id, @PathVariable String oldPwd, @PathVariable String newPwd) {
        usersService.updatePassword(id, oldPwd, newPwd);
        return Result.ok();
    }

    //重置用户密码
    @PostMapping("resetPwd/{mobile}/{password}")
    public Result resetPwd(@PathVariable String mobile, @PathVariable String password) {
        usersService.resetPassword(mobile, password);
        return Result.ok();
    }

    //分页条件查询用户
    @PostMapping("getUserListPage/{current}/{limit}")
    public Result getUserListPage(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) UserQuery userQuery) {
        //创建page对象
        Page<User> pageUser = new Page<>(current, limit);

        //构建条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //多条件组合查询（某一条件可能有也可能没有）
        //判断条件值是否为空，不过不为空则拼接条件
        String nickname = userQuery.getNickname();
        String mobile = userQuery.getMobile();
        Integer level = userQuery.getLevel();
        String begin = userQuery.getBegin();
        String end = userQuery.getEnd();
        if(!StringUtils.isEmpty(nickname)){
            wrapper.like("nickname", nickname);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level", level);
        }
        if(!StringUtils.isEmpty(mobile)){
            wrapper.eq("mobile", mobile);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("create_time", begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("create_time", end);
        }

        //排序
        wrapper.orderByDesc("create_time");

        //调用方法，实现分页
        //调用方法的时候，底层封装，把分页所有数据封装到pageUser对象里面
        usersService.page(pageUser, wrapper);

        //总记录数
        long total = pageUser.getTotal();
        //数据list集合
        List<User> records = pageUser.getRecords();

        return Result.ok().data("total", total).data("rows", records);
    }

    //删除用户
    @DeleteMapping("deleteUser/{id}")
    public Result deleteUser(@PathVariable String id) {
        boolean flag = usersService.removeById(id);
        return flag ? Result.ok() : Result.error();
    }

    //添加用户
    @PostMapping("addUser")
    public Result addUser(@RequestBody User user) {
        usersService.save(user);
        return Result.ok();
    }

    //注销用户或者撤销注销
    @GetMapping("disableUser/{id}")
    public Result disableUser(@PathVariable String id) {
        usersService.disableUser(id);
        return Result.ok();
    }

}

