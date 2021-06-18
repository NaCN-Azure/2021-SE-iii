package com.heap.userservice.service.impl;

import com.heap.userservice.UserApplication;
import com.heap.commonutils.Result;
import com.heap.userservice.entity.User;
import com.heap.userservice.entity.vo.LoginVo;
import com.heap.userservice.entity.vo.RegisterVO;
import com.heap.userservice.entity.vo.UserInfoVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = UserApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DirtiesContext
public class UsersServiceImplTest {
    @Autowired
    UsersServiceImpl usersService;
    @Test
    public void login() {
        LoginVo loginVo=new LoginVo();
        loginVo.setMobile("15809571672");
        loginVo.setPassword("nsy123@");
//        String a = usersService.login(loginVo).toString();
//        assertNotNull(a,null);
    }

    @Test
    public void register() {
        RegisterVO registerVO = new RegisterVO();
        registerVO.setMobile("145655585555");
        registerVO.setNickname("仙人");
        registerVO.setPassword("789qqq!");
//        usersService.register(registerVO);
        LoginVo loginVo=new LoginVo();
        loginVo.setMobile("145655585555");
        loginVo.setPassword("789qqq!");
//        String a = usersService.login(loginVo).toString();
//        assertNotNull(a,null);
    }

    @Test
    public void getUserInfo() {
        User user = new User();
        UserInfoVO u = new UserInfoVO();
//        UserInfoVO userInfoVO= usersService.getUserInfo(user);
//        assertEquals(u,userInfoVO);
    }

    @Test
    public void updateInfo() {
        User user = new User();
        user.setIsVip(true);
//        UserInfoVO userInfoVO = usersService.getUserInfo(user);
//        usersService.updateInfo(userInfoVO);
//        assert (userInfoVO.getIsVip());
    }

    @Test
    public void updateAvatar() {
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setAvatar("88888888");
//        usersService.updateAvatar(userInfoVO);
//        assertEquals("88888888",userInfoVO.getAvatar());
    }

    @Test
    public void updatePassword() {
        LoginVo loginVo=new LoginVo();
        loginVo.setMobile("15809571672");
        loginVo.setPassword("nsy123!");
//        usersService.updatePassword("14526658852","nsy123@","nsy123!");
        //        String a = usersService.login(loginVo).toString();
//        assertNotNull(a,null);
    }

    @Test
    public void resetPassword() {
        LoginVo loginVo=new LoginVo();
        loginVo.setMobile("15809571672");
        loginVo.setPassword("nsy123!");
//        usersService.resetPassword("15809571672","nsy123!");
        //        String a = usersService.login(loginVo).toString();
//        assertNotNull(a,null);
    }

    @Test
    public void disableUser() {
//        usersService.disableUser("14526658852");
        LoginVo loginVo=new LoginVo();
        loginVo.setMobile("15809571672");
        loginVo.setPassword("nsy123@");
//        String a = usersService.login(loginVo).toString();
//        assertNull(a);
    }

    @Test
    public void updateUserInfoAdmin() {
        User user = new User();
        user.setIsVip(true);
//        UserInfoVO userInfoVO= usersService.getUserInfo(user);
//        UserInfoVO A = usersService.updateUserInfoAdmin(userInfoVO);
//        usersService.updateInfo(userInfoVO);
//        assert (A.getIsVip());
    }

    @Test
    public void addUser() {
        User user = new User();
        user.setMobile("145888965554");
        user.setPassword("HJH111&");
//        usersService.addUser(user);
        LoginVo loginVo=new LoginVo();
        loginVo.setMobile("145888965554");
        loginVo.setPassword("HJH111&");
//        String a = usersService.login(loginVo).toString();
//        assertNotNull(a,null);
    }

    @Test
    public void deleteUser() {
//        usersService.deleteUser("14585652252");
        LoginVo loginVo=new LoginVo();
        loginVo.setMobile("15809571672");
        loginVo.setPassword("nsy123@");
//        String a = usersService.login(loginVo).toString();
//        assertNull(a);
    }

    @Test
    public void setVipUser() {
        User user = new User();
        user.setMobile("15809571672");
        user.setPassword("nsy123@");
//        usersService.setVipUser("1458562252",85);
//        UserInfoVO userInfoVO=usersService.getUserInfo(user);
//        assert (userInfoVO.getIsVip());
    }
}
