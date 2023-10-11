package com.catGod.userCenter;

import com.catGod.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by lily via on 2023/9/19 10:18
 */
@SpringBootTest
public class userServiceTest {

    @Autowired
    private UserService userService;


    @Test
    void userRegisterTest(){
        String username = "lilyWhen";
        String password = "12345678";
        String checkPassword = "12345678";
        assert userService.userRegister(username, password, checkPassword)!=-1;
        // 用戶名不合法
        username = "li";
        assert userService.userRegister(username, password, checkPassword)==-1;

        // 密码不合法
        username = "lily";
        password = "123456";
        assert userService.userRegister(username, password, checkPassword)==-1;

        // 两次密码不一致
        password = "12345678";
        checkPassword = "12345690";
        assert userService.userRegister(username, password, checkPassword)==-1;

        // 用户已存在
        username = "鱼皮";
        password = "12345678";
        checkPassword = "12345678";;
        assert userService.userRegister(username, password, checkPassword)==-1;

    }
}
