package com.findcup.pydeal.controller;


import com.findcup.pydeal.common.Constants;
import com.findcup.pydeal.common.Result;
import com.findcup.pydeal.common.ResultGenerator;
import com.findcup.pydeal.config.annotation.TokenToUser;
import com.findcup.pydeal.entity.Deal;
import com.findcup.pydeal.entity.User;
import com.findcup.pydeal.service.DealService;
import com.findcup.pydeal.utils.PageUtil;
//import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
//import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.javassist.bytecode.ConstantAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
//import sun.jvm.hotspot.debugger.Page;

//import java.lang.reflect.GenericArrayType;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/deals")
public class DealController {

    @Autowired
    private DealService dealService;


    @RequestMapping(value = "/list", method = RequestMethod.POST)
    Result List(@RequestBody Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genErrorResult(Constants.RESULT_CODE_PARAM_ERROR, "缺少字段");
        }
        PageUtil pageutil = new PageUtil(params);
        return ResultGenerator.genSuccessResult(dealService.getDeals(pageutil));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    Result Update(@RequestBody Deal deal, @TokenToUser User user) {
        // TODO: 增加postExists
        if (user == null) {
            return ResultGenerator.genFailResult("未登录");
        }
        String state = deal.getState();
        if (deal.getState()==""){
            deal.setState(state);
        }else{
            deal.setState("pending");
        }
        String [] allowedState= {"overdue","pending","terminated"};
        if (!Arrays.asList(allowedState).contains(deal.getState())){
            return ResultGenerator.genFailResult("状态值不合法");
        }
        deal.setUid(user.getUid());
        // TODO:接取者也可将状态恢复至 pending --- BUG
        if (dealService.UpdateDealTerminator(deal)!=1){
            return ResultGenerator.genFailResult("接取失败");
        }
        if (dealService.UpdateDealState(deal) == 1) {
            return ResultGenerator.genSuccessResult("更新状态成功");
        } else {
            return ResultGenerator.genErrorResult(Constants.RESULT_CODE_SERVER_ERROR, "更新失败");
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    Result Add(@RequestBody Deal deal, @TokenToUser User user) {
        // TODO:检查用户是否存在 增加userExists
        // TODO:增加ddl检查
        if (user == null) {
            return ResultGenerator.genFailResult("未登录");
        }
        deal.setUid(user.getUid());
        if (dealService.insertDeal(deal) == 1) {
            return ResultGenerator.genSuccessResult("发布成功");
        } else {
            return ResultGenerator.genFailResult("发布失败");
        }
    }
}
