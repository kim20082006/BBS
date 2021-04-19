package com.javaee.bbs1.util;

import com.javaee.bbs1.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author: tianzihe
 * @date: 2020/11/3 - 19:13
 * @mail: maxtian123@hotmail.com
 * @info:
 */
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }


}
