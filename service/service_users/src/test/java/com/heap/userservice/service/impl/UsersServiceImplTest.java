package com.heap.userservice.service.impl;

import com.heap.userservice.UserApplication;
import com.heap.commonutils.Result;
import com.heap.userservice.entity.vo.LoginVo;
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
    }

    @Test
    public void register() {
    }

    @Test
    public void getUserInfo() {
    }

    @Test
    public void updateInfo() {
    }

    @Test
    public void updateAvatar() {
    }

    @Test
    public void updatePassword() {
    }

    @Test
    public void resetPassword() {
    }

    @Test
    public void disableUser() {
    }

    @Test
    public void updateUserInfoAdmin() {
    }

    @Test
    public void addUser() {
    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void setVipUser() {
    }
}