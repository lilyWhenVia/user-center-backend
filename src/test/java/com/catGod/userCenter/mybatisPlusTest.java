package com.catGod.userCenter;

import com.catGod.model.domain.User;
import com.catGod.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by lily via on 2023/9/18 16:52
 */
@SpringBootTest
public class mybatisPlusTest {

    @Autowired
    private UserService userService;

    @Test
    void testQuery(){

        List<User> users =userService.list();


        users.forEach(System.out::println);
    }


}
