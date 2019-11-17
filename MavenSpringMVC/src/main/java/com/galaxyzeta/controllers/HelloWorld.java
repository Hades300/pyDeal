package com.galaxyzeta.controllers;

import com.galaxyzeta.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

@Controller
public class HelloWorld {
    @ResponseBody
    @RequestMapping(value="/index", method= RequestMethod.POST)
    public String sayHello(User user){
        System.out.println(user.toString());
        System.out.println("Vis");
        return "Hello";
    }
}
