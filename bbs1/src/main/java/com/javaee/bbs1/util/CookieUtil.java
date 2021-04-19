package com.javaee.bbs1.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: tianzihe
 * @date: 2020/11/4 - 12:49
 * @mail: maxtian123@hotmail.com
 * @info:
 */

public class CookieUtil {

    public static String getValue(HttpServletRequest request,String name){
        if (request == null || name == null){
            throw new IllegalArgumentException("参数为空！");
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies){
                if (cookie.getName().equals(name)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
