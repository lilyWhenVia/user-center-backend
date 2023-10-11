package com.catGod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.catGod.constant.ErrorCode;
import com.catGod.constant.UserConstant;
import com.catGod.exception.UserException;
import com.catGod.model.domain.User;
import com.catGod.service.UserService;
import com.catGod.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.catGod.constant.ErrorCode.*;

/**
 * @author 22825
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2023-09-17 16:38:56
 */
@Service
//@Mapper
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private final int passwordLen = 8;
    private final int userAccountLen = 4;

    private static final String SALT = "lily";

    /**
     * 封装mapper层
     */
    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
//       校验用户的账户名合法、密码和校验密码合法且相同
        /*非空*/
        if ( !StringUtils.hasLength(userAccount) || !StringUtils.hasLength(userPassword) || !StringUtils.hasLength(checkPassword) || !userPassword.equals(checkPassword)) {
            throw new UserException("账户名或密码为空或两次密码不一致");
        }
        /*
        账户长度不小于4位
        密码长度不小于8位
         */
        if (userAccount.length() < userAccountLen || userPassword.length() < passwordLen) {
            throw new UserException("username or password len error");
        }
        /*
        密码不包含特殊字符
         */
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
        //testStr被检测的文本
        Matcher matcher = pattern.matcher(userPassword);
        //匹配上的时候返回true,匹配不通过返回-1
        if (!matcher.matches()) {
            throw new UserException("password contains special characters");
        }

        /*
        账户不能重复
         */
        User user = new User();
        long count = this.count(new QueryWrapper<>(user).eq("userAccount", userAccount));
        if (count > 0) {
            throw new UserException("username already exists");
        }

        /*
        密码加密，把用户存入数据库
         */
        // 基于spring框架中的DigestUtils工具类进行密码加密
        String hashPassword = DigestUtils.md5DigestAsHex((userPassword + SALT).getBytes());

        user.setUserAccount(userAccount);
        user.setUserPassword(hashPassword);

        boolean saveRes = this.save(user);
        if (!saveRes) {
            throw new UserException("");
        }

        return user.getId();
    }

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
//       校验用户的账户名合法、密码和校验密码合法且相同
        /*非空*/
        if ( !StringUtils.hasLength(userAccount) || !StringUtils.hasLength(userPassword) || !StringUtils.hasLength(checkPassword) || !userPassword.equals(checkPassword) || !StringUtils.hasLength(planetCode)){
            throw new UserException(WRONG_PARAM_ERR.getCode(), WRONG_PARAM_ERR.getMessage(), "账户名或密码为空或两次密码不一致或星球编号为空");
        }
        /*
        账户长度不小于4位
        密码长度不小于8位
         */
        if (userAccount.length() < userAccountLen || userPassword.length() < passwordLen) {
            throw new UserException(WRONG_PARAM_ERR.getCode(), WRONG_PARAM_ERR.getMessage(),"username or password len error");
        }
        /*
        星球编号1-5位数
         */
        if (planetCode.length() > 5){
            throw new UserException(WRONG_PARAM_ERR.getCode(), WRONG_PARAM_ERR.getMessage(),"planetCode len error");
        }

        /*
        密码不包含特殊字符
         */
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
        //testStr被检测的文本
        Matcher matcher = pattern.matcher(userPassword);
        //匹配上的时候返回true,匹配不通过返回-1
        if (!matcher.matches()) {
            throw new UserException(WRONG_PARAM_ERR.getCode(), WRONG_PARAM_ERR.getMessage(),"password contains special characters");
        }

        /*
        账户不能重复
         */
        User user = new User();
        long count = userMapper.selectCount(new QueryWrapper<>(user).eq("userAccount", userAccount));
        if (count > 0) {
            throw new UserException(ACCOUNT_NOT_FOUND_CODE.getCode(), ACCOUNT_NOT_FOUND_CODE.getMessage(),"username already exists");
        }

        /*
        星球编号不能重复
         */
        count = this.count(new QueryWrapper<>(user).eq("planetCode", planetCode));
        if (count > 0) {
            throw new UserException(WRONG_PARAM_ERR.getCode(), WRONG_PARAM_ERR.getMessage(),"planetCode already exists");
        }

        /*
        密码加密，把用户存入数据库
         */
        // 基于spring框架中的DigestUtils工具类进行密码加密
        String hashPassword = DigestUtils.md5DigestAsHex((userPassword + SALT).getBytes());


        user.setUserAccount(userAccount);
        user.setUserPassword(hashPassword);
        user.setPlanetCode(planetCode);

        boolean saveRes = this.save(user);
        if (!saveRes) {
            throw new UserException(SYSTEM_ERR.getCode() , SYSTEM_ERR.getMessage(), "database save error");
        }

        return user.getId();
    }


    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        /*非空*/
        if ( !StringUtils.hasLength(userAccount) || !StringUtils.hasLength(userPassword) ) {
            throw new UserException(WRONG_PARAM_ERR.getCode(), WRONG_PARAM_ERR.getMessage(),"账户名或密码为空");
        }
        /*
        账户长度不小于4位
        密码长度不小于8位
         */
        if (userAccount.length() < userAccountLen || userPassword.length() < passwordLen) {
            throw new UserException(WRONG_PARAM_ERR.getCode(), WRONG_PARAM_ERR.getMessage(),"username or password len error");

        }
        /*
        密码不包含特殊字符
         */
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
//testStr被检测的文本
        Matcher matcher = pattern.matcher(userPassword);
//匹配上的时候返回true,匹配不通过返回-1
        if (!matcher.matches()) {
            throw new UserException(WRONG_PARAM_ERR.getCode(), WRONG_PARAM_ERR.getMessage(),"password contains special characters");

        }
        // 校验密码是否正确
        String hashPassword = DigestUtils.md5DigestAsHex((userPassword + SALT).getBytes());
        User user = this.getOne(new QueryWrapper<>(new User()).eq("userAccount", userAccount).eq("userPassword", hashPassword));

        if (user == null){
            throw new UserException(WRONG_PARAM_ERR.getCode(), WRONG_PARAM_ERR.getMessage(),"username or password error");
        }

        // 用户脱敏
        User safetyUser = this.getSafetyUser(user);

        // 存入登录态
        request.getSession().setAttribute(UserConstant.USER_LONGING_STATE, safetyUser);


        return safetyUser;

    }

    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(UserConstant.USER_LONGING_STATE);
        return 1;
    }


    /**
     * 根据用户id获取用户信息
     * @param id
     * @return 返回脱敏后用户信息
     */
    @Override
    public User getUserById(long id) {
        if (id <= 0) {
            throw new UserException(ACCOUNT_NOT_FOUND_CODE.getCode(), ACCOUNT_NOT_FOUND_CODE.getMessage(),"用户不存在");
        }
        User user = this.getOne(new QueryWrapper<>(new User()).eq("id", id));
        return this.getSafetyUser(user);
    }

    @Override
    public List<User> getUserByName(String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        QueryWrapper<User> qw = userQueryWrapper.like("username", username);
        List<User> users = this.list(qw);
        if (users == null) {
            throw new UserException(ACCOUNT_NOT_FOUND_CODE.getCode(), ACCOUNT_NOT_FOUND_CODE.getMessage(),"用户不存在");
        }
        //    脱敏用户
        List<User> safetyUsers = users.stream().peek(this::getSafetyUser).collect(Collectors.toList());
        return safetyUsers;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = this.list();
        if (users == null) {
            throw new UserException(ACCOUNT_NOT_FOUND_CODE.getCode(), ACCOUNT_NOT_FOUND_CODE.getMessage(),"用户不存在");
        }
        //    脱敏用户
        List<User> safetyUsers = users.stream().peek(this::getSafetyUser).collect(Collectors.toList());
        return safetyUsers;
    }


    /**
     * 用户脱敏
     * @param user
     * @return 无密码的用户
     */
    private User getSafetyUser(User user){
        User safetyUser = new User();
        safetyUser.setUsername(user.getUsername());
        safetyUser.setCreateTime(user.getCreateTime());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setGender(user.getGender());;
        safetyUser.setUserRole(user.getUserRole());;
        safetyUser.setUserStatus(user.getUserStatus());;
        safetyUser.setUserAccount(user.getUserAccount());;
        return safetyUser;
    }
}




