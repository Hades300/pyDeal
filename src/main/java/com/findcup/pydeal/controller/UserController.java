package com.findcup.pydeal.controller;

import com.findcup.pydeal.common.Constants;
import com.findcup.pydeal.common.Result;
import com.findcup.pydeal.common.ResultGenerator;
import com.findcup.pydeal.config.annotation.TokenToUser;
import com.findcup.pydeal.entity.User;
import com.findcup.pydeal.service.UserService;
import com.findcup.pydeal.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result list(@RequestBody Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genErrorResult(Constants.RESULT_CODE_PARAM_ERROR, "参数异常！");
        }
        //查询列表数据
        PageUtil pageUtil = new PageUtil(params);
        return ResultGenerator.genSuccessResult(userService.getUserPage(pageUtil));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody User user) {
        Result result = ResultGenerator.genFailResult("登陆失败");
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            result.setMessage("请填写登录信息！");
        }
        User loginUser = userService.updateTokenAndLogin(user.getUsername(), user.getPassword());
        if (loginUser != null) {
            result = ResultGenerator.genSuccessResult(loginUser);
        }
        return result;
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result save(@RequestBody User user) {
        System.out.println(user);
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword()) || user.getStudentId() <= 0 || user.getGender() <= 0) {
            return ResultGenerator.genErrorResult(Constants.RESULT_CODE_PARAM_ERROR, "参数异常！");
        }
        User tempUser = userService.selectByUserName(user.getUsername());
        if (tempUser != null) {
            return ResultGenerator.genErrorResult(Constants.RESULT_CODE_PARAM_ERROR, "用户已存在勿重复添加！");
        }
        if ("admin".endsWith(user.getUsername().trim())) {
            return ResultGenerator.genErrorResult(Constants.RESULT_CODE_PARAM_ERROR, "不能添加admin用户！");
        }
        if (userService.save(user) > 0) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("添加失败");
        }
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestBody User user, @TokenToUser User loginUser) {
        String token = "";
        try {
            token = loginUser.getToken();
        } catch(Exception e) {
            return ResultGenerator.genFailResult("未登录！");
        }
        
        if (StringUtils.isEmpty(loginUser) || StringUtils.isEmpty(token)) {
            return ResultGenerator.genErrorResult(Constants.RESULT_CODE_NOT_LOGIN, "未登录！");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            return ResultGenerator.genErrorResult(Constants.RESULT_CODE_PARAM_ERROR, "请输入密码！");
        }
        User tempUser = userService.selectById(user.getUid());
        if (tempUser == null) {
            return ResultGenerator.genErrorResult(Constants.RESULT_CODE_PARAM_ERROR, "无此用户！");
        }
        if ("admin".endsWith(tempUser.getUsername().trim())) {
            return ResultGenerator.genErrorResult(Constants.RESULT_CODE_PARAM_ERROR, "不能修改admin用户！");
        }
        tempUser.setPassword(user.getPassword());
        if (userService.updatePassword(user) > 0) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("修改失败");
        }
    }

    @RequestMapping(value = "/debug",method = RequestMethod.POST)
    public Result debug(){
        Result res = ResultGenerator.genSuccessResult("OK");
        res.setData(123);
        return res;
    }
}
