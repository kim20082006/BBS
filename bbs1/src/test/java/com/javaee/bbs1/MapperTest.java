package com.javaee.bbs1;

import com.javaee.bbs1.dao.DiscussPostMapper;
import com.javaee.bbs1.dao.LoginTicketMapper;
import com.javaee.bbs1.dao.MessageMapper;
import com.javaee.bbs1.dao.UserMapper;
import com.javaee.bbs1.entity.DiscussPost;
import com.javaee.bbs1.entity.LoginTicket;
import com.javaee.bbs1.entity.Message;
import com.javaee.bbs1.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author: tianzihe
 * @date: 2020/9/30 - 9:06
 * @mail: maxtian123@hotmail.com
 * @info:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BBS1Application.class)
    public class MapperTest {
        @Autowired
        private UserMapper userMapper;

        @Autowired
        private DiscussPostMapper discussPostMapper;

        @Autowired
        private LoginTicketMapper loginTicketMapper;

        @Autowired
        private MessageMapper messageMapper;

        @Test
        public void testSelectUser(){
            User user = userMapper.selectById(13);
            System.out.println(user);

            user = userMapper.selectByName("13");
            System.out.println(user);

            user = userMapper.selectByEmail("13@sina.com");
            System.out.println(user);
        }

//    @Test
//    public void testInsertUser(){
//        User user = new User();
//        user.setUsername("test133");
//        user.setPassword("1234567");
//        user.setSalt("qwert");
//        user.setEmail("133@qq.com");
//        user.setHeaderUrl("https://hbimg.huabanimg.com/d8e0f81d4197c0a278d9b1c5e10711cd30eecae4d3b7-1194sH_fw658/format/webp");
//        user.setCreateTime(new Date());
//        int row = userMapper.insertUser(user);
//        System.out.println(row);
//        System.out.println(user.getId());
//    }

    @Test
    public void updateUser(){

            int row = userMapper.updateStatus(150,1);
            System.out.println(row);

        row = userMapper.updatePassword(150,"45667899");
        System.out.println(row);

        row = userMapper.updateHeader(150,"https://hbimg.huabanimg.com/bc11c946cef40c4d76b6d6c9ff76aeb3ca3f64375d9a-5BxqR9_fw658/format/webp");
        System.out.println(row);

    }

    @Test
    public void testSelectPosts(){


            List<DiscussPost> list = discussPostMapper.selectDiscussPosts(0,0,10);
            for (DiscussPost post:list){
                System.out.println(post);
            }

            int rows = discussPostMapper.selectDiscussPostRows(0);
            System.out.println(rows);


    }

    @Test
    public void testInsertLoginTicket() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(101);
        loginTicket.setTicket("abc");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 10));

        loginTicketMapper.insertLoginTicket(loginTicket);
    }

    @Test
    public void testSelectLoginTicket() {
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);

        loginTicketMapper.updateStatus("abc", 1);
        loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);
    }

    @Test
    public void testSelectLetters() {
        List<Message> list = messageMapper.selectConversations(111, 0, 20);
        for (Message message : list) {
            System.out.println(message);
        }

        int count = messageMapper.selectConversationCount(111);
        System.out.println(count);

        list = messageMapper.selectLetters("111_112", 0, 10);
        for (Message message : list) {
            System.out.println(message);
        }

        count = messageMapper.selectLetterCount("111_112");
        System.out.println(count);

        count = messageMapper.selectLetterUnreadCount(131, "111_131");
        System.out.println(count);

    }
}


