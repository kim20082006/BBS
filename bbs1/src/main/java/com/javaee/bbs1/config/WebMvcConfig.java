package com.javaee.bbs1.config;

import com.javaee.bbs1.controller.Interceptor.ExInterceptor;
import com.javaee.bbs1.controller.Interceptor.LoginTicketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: tianzihe
 * @date: 2020/11/4 - 12:40
 * @mail: maxtian123@hotmail.com
 * @info:
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ExInterceptor exInterceptor;

    @Autowired
    private LoginTicketInterceptor loginTicketInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(exInterceptor)
                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg")
                .addPathPatterns("/register", "/login");

        registry.addInterceptor(loginTicketInterceptor)
                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");


    }

}
