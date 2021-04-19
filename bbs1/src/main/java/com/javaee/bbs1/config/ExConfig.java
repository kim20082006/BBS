package com.javaee.bbs1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * @author: tianzihe
 * @date: 2020/9/11 - 14:35
 * @mail: maxtian123@hotmail.com
 * @info:
 */
@Configuration
public class ExConfig {


    @Bean
    public SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    }

}
