package com.javaee.bbs1.dao;

import com.javaee.bbs1.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: tianzihe
 * @date: 2020/9/22 - 15:19
 * @mail: maxtian123@hotmail.com
 * @info:
 */
@Mapper
public interface UserMapper {
    User selectById(int id);
    User selectByName(String username);
    User selectByEmail(String email);
    int insertUser(User user);
    int updateStatus(int id,int status);
    int updateHeader(int id,String headerUrl);
    int updatePassword(int id,String password);

}
