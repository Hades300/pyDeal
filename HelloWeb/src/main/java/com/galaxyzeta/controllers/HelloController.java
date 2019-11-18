package com.galaxyzeta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import com.galaxyzeta.pojo.User;
import java.util.Map;

@Controller
public class HelloController {
    @ResponseBody
    @RequestMapping(value="/index", method= RequestMethod.POST)
    public String sayHello(@RequestBody User user){
        System.out.println(user);
        return "Hello";
    }
}
