package com.javaee.bbs1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: tianzihe
 * @date: 2020/9/11 - 11:25
 * @mail: maxtian123@hotmail.com
 * @info:
 */
@Controller
@RequestMapping("/ex")
public class ExController{

    @RequestMapping("/example")
    @ResponseBody
    public String example() {

        return "Hello world.";
    }


    //获取get请求
    @RequestMapping(path = "/exget",method = RequestMethod.GET)
    @ResponseBody
    public String exGetWeb(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false,defaultValue = "10" ) int limit){
        System.out.println(current);
        System.out.println(limit);
        return "This is an example, succeed";
    }
    @RequestMapping(path = "/exget/{number}",method = RequestMethod.GET)
    @ResponseBody
    public  String exGetNumber(@PathVariable("number") int number){
        System.out.println(number);
        return "example";
    }
    //post 请求
    @RequestMapping(path = "/customer", method = RequestMethod.POST)
    @ResponseBody
    public String saveCustomer(String name,int age){
        System.out.println(name);
        System.out.println(age);
        return "成功";
    }
    //响应Html数据
    @RequestMapping(path = "/company", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getCompany(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","腾讯");
        mav.addObject("game","云顶");
        mav.setViewName("/demo/view");
        return mav;
    }

    @RequestMapping(path = "/school",method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name","滨海学院");
        model.addAttribute("age",26);
        return "/demo/view";
    }

    //响应JSON数据
    @RequestMapping(path = "/student", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getStudent(){
        Map<String,Object> student=new HashMap<>();
        student.put("name","王一");
        student.put("age",18);
        student.put("points",80.00);
        return student;
    }

}