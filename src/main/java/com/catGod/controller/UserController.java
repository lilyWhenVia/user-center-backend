package com.catGod.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.catGod.commons.BaseResponse;
import com.catGod.commons.ResponseUtil;
import com.catGod.constant.UserConstant;
import com.catGod.model.domain.User;
import com.catGod.model.request.requestUser;
import com.catGod.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by lily via on 2023/9/18 17:30
 */
@RestController("/user")
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 用户注销
     */
    @GetMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request){

        if (request == null){
            return ResponseUtil.unLogin();
        }
        Integer data = userService.userLogout(request);
        return ResponseUtil.success(data);
    }


    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody requestUser requestUser) {
        String userAccount = requestUser.getUserAccount();
        String userPassword = requestUser.getUserPassword();
        String checkPassword = requestUser.getCheckPassword();
        // plantCode
        String planetCode = requestUser.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)){
            return ResponseUtil.wrongParam("账户名或密码或星球号为空");
        }
        Long id = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        return ResponseUtil.success(id);
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody requestUser requestUser, HttpServletRequest request) {

        if (request == null || requestUser == null){
            return ResponseUtil.unLogin();
        }

        String userAccount = requestUser.getUserAccount();
        String userPassword = requestUser.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return ResponseUtil.wrongParam("账号或密码为空");
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResponseUtil.success(user);
    }


    /**
     * 获取当前用户信息
     */
    @GetMapping("/getUser")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        if (request == null){
            return ResponseUtil.unLogin();
        }
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LONGING_STATE);
        if (user == null || user.getUserAccount() == null ) {
            return ResponseUtil.accountNotFound("请先登录");
        }
        User data = userService.getOne(new QueryWrapper<>(user).eq("userAccount", user.getUserAccount()));
        return ResponseUtil.success(data);
    }

    /**
     * 根据用户名查询多条用户数据
     */
    @GetMapping("/searchUser")
    public BaseResponse<List<User>> getUserByName(@RequestParam("username") String username, HttpServletRequest request) {
        if (request == null){
            return ResponseUtil.unLogin();
        }

    if (! checkPermission(request)){
        return ResponseUtil.unAuth();
    }

    if (StringUtils.isBlank(username)) {

            return ResponseUtil.wrongParam("用户名为空");
        }

        List<User> users = userService.getUserByName(username);
        return ResponseUtil.success(users);
    }



    @GetMapping("/search")
    public BaseResponse<List<User>> getUsers(HttpServletRequest request) {

        if (request == null){
            return ResponseUtil.unLogin();
        }

    if (! checkPermission(request)){
        return ResponseUtil.unAuth();
    }


        List<User> users = userService.getUsers();
        return ResponseUtil.success(users);
    }




    /**
     * 删除
     */
    @PostMapping("/deleteUser")
    public BaseResponse<Boolean> deleteUser(@RequestParam("username") String username, HttpServletRequest request) {
        if (! checkPermission(request)){
            return ResponseUtil.unAuth();
        }
        if (StringUtils.isBlank(username)) {
            return ResponseUtil.wrongParam("用户名为空");
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        QueryWrapper<User> qw = userQueryWrapper.eq("username", username);
        Boolean remove = userService.remove(qw);
        return ResponseUtil.success(remove);
    }

    /**
     * 查询权限
     */
    private boolean checkPermission(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LONGING_STATE);
        if (user == null) {
            return false;
        }
        return user.getUserRole() == 1;
    }

}
