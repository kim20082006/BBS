package com.javaee.bbs1.controller.advice;

import com.javaee.bbs1.util.Bbs1Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: tianzihe
 * @date: 2020/12/10 - 18:57
 * @mail: maxtian123@hotmail.com
 * @info:
 */
@ControllerAdvice(annotations = Controller.class)
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);
    @ExceptionHandler({Exception.class})
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("服务器发生了异常："+e.getMessage());
        for (StackTraceElement element: e.getStackTrace()){
            logger.error(element.toString());
        }

        String xRequestWith = request.getHeader("x-requested-with");
        if ("XMLHttpRequest".equals(xRequestWith)){
            response.setContentType("application/plain;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(Bbs1Util.getJSONString(1,"服务器异常！"));
        } else{
            response.sendRedirect(request.getContextPath()+"/error");
        }
    }
}
