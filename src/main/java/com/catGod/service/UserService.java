package com.catGod.service;

import com.catGod.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 22825
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-09-17 16:38:56
*/
@Service
public interface UserService extends IService<User> {

    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

     long userRegister(String userAccount, String userPassword, String checkPassword);

    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    int userLogout(HttpServletRequest request);

    User getUserById(long id);

    List<User> getUserByName(String username);

    List<User> getUsers();


}
