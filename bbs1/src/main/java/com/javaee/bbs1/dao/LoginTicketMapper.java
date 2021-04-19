package com.javaee.bbs1.dao;

import com.javaee.bbs1.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @author: tianzihe
 * @date: 2020/10/29 - 19:14
 * @mail: maxtian123@hotmail.com
 * @info:
 */
@Mapper
public interface LoginTicketMapper {

    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select id,user_id,ticket,status,expired ",
            "from login_ticket where ticket=#{ticket}"
    })
    LoginTicket selectByTicket(String ticket);

    @Update({
            "update login_ticket set status=#{status} where ticket=#{ticket}"
    })
    int updateStatus(String ticket, int status);
}
