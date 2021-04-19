package com.javaee.bbs1;

import com.javaee.bbs1.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author: tianzihe
 * @date: 2020/10/15 - 20:37
 * @mail: maxtian123@hotmail.com
 * @info:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BBS1Application.class)
public class MailTests {
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;
/*
    @Test
    public void testContentMail(){
        mailClient.sendMail(
                "453901274@qq.com",
                "TEST",
                "It is just a test."
        );
    }

    @Test
    public void testHtmlMail(){
        Context context = new Context();
        context.setVariable("username","张三");
        String content = templateEngine.process("/mail/demo",context);
        System.out.println(content);
        mailClient.sendMail("453901274@qq.com","HELLO",content);
    }

 */
}
