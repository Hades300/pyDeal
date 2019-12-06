package com.galaxyzeta.controllers;

import com.galaxyzeta.dao.TestMapper;
import com.galaxyzeta.beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HelloWorldController {

    //Spring注解，在Ioc容器中注册testMapper这个bean
    @Autowired
    HelloWorldController(TestMapper testMapper){
        this.testMapper = testMapper;
    }
    private TestMapper testMapper;
    /*
    //Thymeleaf模板引擎测试
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String dynamicPageDemo(){
        return "leaf";
    }

    //跳转到form页面
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String formPage(){
        return "formTest";
    }

    //Ajax表单传输 范例 如果用户不存在就写入数据库
    @RequestMapping(value = "/ajax", method = RequestMethod.POST)
    @ResponseBody
    public String formAjaxDemo(@RequestBody User user){
        System.out.println(user.toString());
        User queryUser = testMapper.getUser(user.getUsername());
        if(queryUser == null){
            testMapper.setUser(user);
            System.out.println("[Service]Data Injected...");
        } else {
            System.out.println("[Service]User already exist!");
        }
        return "Received";
    }

    //Form表单传输, 响应体打印到新的页面上
    @RequestMapping(value = "/form2", method = RequestMethod.POST)
    @ResponseBody
    public User formSubmitDemo(User user){
        return user;
    }

    //Form表单传输, 返回信息用thymeleaf模板引擎渲染
    @RequestMapping(value = "/form3", method = RequestMethod.POST)
    public ModelAndView formSubmitWithThymeleafRender(User user){
        ModelAndView modelAndView = new ModelAndView("welcome");
        modelAndView.addObject("username", user.getUsername());
        modelAndView.addObject("password", user.getPassword());
        return modelAndView;
    }
    */
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String staticPageDemo(){
        return "hello";
    }

    @RequestMapping(value="/form", method = RequestMethod.POST)
    @ResponseBody
    public User staticPageForm(User user){
        return user;
    }

}
