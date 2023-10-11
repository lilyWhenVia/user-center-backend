package com.catGod.mapper;

import com.catGod.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 22825
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2023-09-17 16:38:56
* @Entity com.catGod.model.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




