package com.galaxyzeta.controllers;

import com.galaxyzeta.beans.Deal;
import com.galaxyzeta.beans.Status;
import com.galaxyzeta.dao.TestMapper;
import com.galaxyzeta.beans.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Controller
public class HelloWorldController {

    //Spring注解，在Ioc容器中注册testMapper这个bean
    @Autowired
    HelloWorldController(TestMapper testMapper){
        this.testMapper = testMapper;
    }
    private TestMapper testMapper;

    //Thymeleaf模板引擎测试
    /*
    @RequestMapping(value = "/th", method = RequestMethod.GET)
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
    //静态页面测试
    //如果想要用静态页面，请在application.properties中注释thymeleaf相关内容
    @RequestMapping(value="/s", method = RequestMethod.GET)
    public String staticPageDemo(){
        return "hello";
    }

    @RequestMapping(value="/sform", method = RequestMethod.POST)
    @ResponseBody
    public User staticPageForm(User user){
        System.out.println(user);
        return user;
    }

    @RequestMapping(value="/sajax", method = RequestMethod.POST)
    @ResponseBody
    public User staticPageAjax(@RequestBody User user){
        System.out.println(user);
        return user;
    }

    /**
     * Return login status (or register status for dev use).
     */
    @RequestMapping(value="/login", method = RequestMethod.POST)
    @ResponseBody
    public Status loginStatus(User user){
        Status status = new Status(200, "[INFO]OK!");
        status.setAvatar("https://image.slidesharecdn.com/bramsey-phptek-2009-http-090522162437-phpapp02/95/making-the-most-of-http-in-your-apps-34-728.jpg?cb=1243035651");
        if(user.getPassword().equals("") || user.getUsername().equals("")){
            status.setStatus(404);
            status.setMessage("[Error]缺少字段");
            status.setAvatar("https://cn.bing.com/th?id=OIP.r_doUPmUg2hlEx8ZDlbnEgHaEL&pid=Api&rs=1");
        }
        return status;
    }

    /**
     * Return register status for dev use.
     */
    @RequestMapping(value="/register", method = RequestMethod.POST)
    @ResponseBody
    public Status register(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String sex = request.getParameter("sex");
        String stuid = request.getParameter("stuid");
        Status status = new Status(200, "[INFO]OK!");
        status.setAvatar("https://image.slidesharecdn.com/bramsey-phptek-2009-http-090522162437-phpapp02/95/making-the-most-of-http-in-your-apps-34-728.jpg?cb=1243035651");
        try{
            if(username.equals("") || sex.equals("") || password.equals("") || stuid.equals("")){
                status.setStatus(404);
                status.setMessage("[Error]缺少字段");
                status.setAvatar("https://cn.bing.com/th?id=OIP.r_doUPmUg2hlEx8ZDlbnEgHaEL&pid=Api&rs=1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            status.setStatus(404);
            status.setMessage("[Error]缺少字段");
            status.setAvatar("https://cn.bing.com/th?id=OIP.r_doUPmUg2hlEx8ZDlbnEgHaEL&pid=Api&rs=1");
            return status;
        }
        return status;
    }

    @GetMapping(value = "/cat")
    @ResponseBody
    public Map<String, Integer> getCategory(){
        Map<String, Integer> map = new LinkedHashMap<String, Integer>();
        map.put("跑腿", 0);
        map.put("找人", 1);
        return map;
    }

    @GetMapping(value = "/deal")
    @ResponseBody
    public ArrayList<HashMap<String, Object>> getDeal(){
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        Deal[] deal = new Deal[2];
        deal[0] = new Deal(0, "五号楼快递帮我拿一下", "求代领快递", new Date(), 0);
        deal[1] = new Deal(0, "三号楼快递帮我拿一下", "求代领快递2", new Date(), 0);
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("context", deal[0].getContext());
        map1.put("title", deal[0].getTitle());
        map1.put("ddl", deal[0].getDdl().toString());
        map1.put("state", deal[0].getState());
        map1.put("catId", deal[0].getCatId());
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("context", deal[1].getContext());
        map2.put("title", deal[1].getTitle());
        map2.put("ddl", deal[1].getDdl().toString());
        map2.put("state", deal[1].getState());
        map2.put("catId", deal[1].getCatId());
        list.add(map1);
        list.add(map2);
        return list;
    }

    @PostMapping(value="/deal")
    @ResponseBody
    public Status postDeal(HttpServletRequest request){
        String username = request.getParameter("catId");
        String password = request.getParameter("");
        String sex = request.getParameter("sex");
        String stuid = request.getParameter("stuid");
        Status status = new Status(200, "[INFO]OK!");
        status.setAvatar("https://image.slidesharecdn.com/bramsey-phptek-2009-http-090522162437-phpapp02/95/making-the-most-of-http-in-your-apps-34-728.jpg?cb=1243035651");
        try{
            if(username.equals("") || sex.equals("") || password.equals("") || stuid.equals("")){
                status.setStatus(404);
                status.setMessage("[Error]缺少字段");
                status.setAvatar("https://cn.bing.com/th?id=OIP.r_doUPmUg2hlEx8ZDlbnEgHaEL&pid=Api&rs=1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            status.setStatus(404);
            status.setMessage("[Error]缺少字段");
            status.setAvatar("https://cn.bing.com/th?id=OIP.r_doUPmUg2hlEx8ZDlbnEgHaEL&pid=Api&rs=1");
            return status;
        }
        return status;
    }
}