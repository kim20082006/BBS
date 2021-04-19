package com.javaee.bbs1;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author: tianzihe
 * @date: 2020/10/15 - 17:15
 * @mail: maxtian123@hotmail.com
 * @info:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BBS1Application.class)
public class LoggerTests {

    private static final Logger logger = LoggerFactory.getLogger(LoggerTests.class);

    @Test
    public void testLogger(){
        System.out.println(logger.getName());
        logger.debug("debug log");
        logger.info("info log");
        logger.warn("warn log");
        logger.error("error log");
    }

}
