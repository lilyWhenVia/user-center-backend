package com.catGod.userCenter;

import com.catGod.controller.UserController;
import com.catGod.model.domain.User;
import com.catGod.service.UserService;
import com.catGod.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
//@RunWith(springboot.class)
class UserCenterApplicationTests{

    @Resource
    UserServiceImpl userService;

    @Resource
    UserController userController;


    @Test
    void testQuery(){
        User user = userService.getById(1);
        assert user != null;
    }

    @Test
    void noTest(){
        System.out.println("no test");
    }
    @Test
    void intTest(){
        int a = 1;
        final int c = 1;

        assert a == c;
    }


    @Test
    void beanTest(){
        Class<? extends UserController> aClass = userController.getClass();
        Assertions.assertNotEquals(null, aClass);
    }


    @Test
    void contextLoads() {
    }

}
