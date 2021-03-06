package com.javaee.bbs1.controller;

import com.google.code.kaptcha.Producer;
import com.javaee.bbs1.entity.User;
import com.javaee.bbs1.service.UserService;
import com.javaee.bbs1.util.BbsConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import static com.javaee.bbs1.util.BbsConstant.ACTIVATION_REPEAT;
import static com.javaee.bbs1.util.BbsConstant.ACTIVATION_SUCCESS;

/**
 * @author: tianzihe
 * @date: 2020/10/16 - 14:23
 * @mail: maxtian123@hotmail.com
 * @info:
 */
@Controller
public class LoginController implements BbsConstant {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptcharProducer;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @RequestMapping(path = "/register",method = RequestMethod.GET)
    public String getRegisterPage(){
        return "/site/register";
    }

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public String register(Model model, User user){
        Map<String,Object> map = userService.register(user);
        if (map == null || map.isEmpty()){
            model.addAttribute("msg","注册成功,我们已向您邮箱发送了一封激活邮件，请您尽快激活！");
            model.addAttribute("target","/index");
            return "/site/operate-result";
        }else{
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            model.addAttribute("emailMsg",map.get("emailMsg"));
            return "/site/register";
        }
    }

    @RequestMapping(path = "/activation/{userId}/{code}",method = RequestMethod.GET)
    public String activation(Model model, @PathVariable("userId") int userId, @PathVariable("code") String code){
        int result = userService.activation(userId,code);
        if (result == ACTIVATION_SUCCESS){
            model.addAttribute("msg","激活成功，您可以使用本账号了！");
            model.addAttribute("target","/login");
        }else if(result == ACTIVATION_REPEAT){
            model.addAttribute("msg","无效操作，该账号之前已被激活！");
            model.addAttribute("target","/index");
        }
        else{
            model.addAttribute("msg","激活失败，激活码不正确！");
            model.addAttribute("target","/index");
        }
        return "/site/operate-result";
    }

    @RequestMapping(path = "/login",method = RequestMethod.GET)
    public String getLoginPage(){
        return "/site/login";
    }

    @RequestMapping(path = "/kaptcha", method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response, HttpSession session){

        String text = kaptcharProducer.createText();
        BufferedImage image = kaptcharProducer.createImage(text);
        session.setAttribute("kaptcha",text);
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image,"png",os);
        } catch (IOException e) {
            logger.error("响应验证码失败：" + e.getMessage());
        }

    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(String username,String password,String code,boolean rememberme,
                        Model model,HttpSession session,HttpServletResponse response) {

        String kaptcha = (String) session.getAttribute("kaptcha");
        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equalsIgnoreCase(code)) {
            model.addAttribute("codeMsg", "验证码不正确!");
            return "/site/login";
        }

    int expiredSeconds = rememberme? REMEMBER_EXPIRED_SECONDS: DEFAULT_EXPIRED_SECONDS;
    Map<String,Object> map = userService.login(username,password,expiredSeconds);
    if (map.containsKey("ticket")){
        Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
        cookie.setPath(contextPath);
        cookie.setMaxAge(expiredSeconds);
        response.addCookie(cookie);
        return "redirect:/index";
    }else {
        model.addAttribute("usernameMsg",map.get("usernameMsg"));
        model.addAttribute("passwordMsg",map.get("passwordMsg"));
        return "/site/login";
    }
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/login";
    }
}
