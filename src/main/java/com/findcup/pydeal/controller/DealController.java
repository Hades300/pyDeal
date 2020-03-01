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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
//import sun.jvm.hotspot.debugger.Page;

//import java.lang.reflect.GenericArrayType;
import java.util.Map;

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
        if (deal.getUid() != user.getUid()) {
            return ResultGenerator.genFailResult("越权操作");
        }
        if (StringUtils.isEmpty(deal.getPid())) {
            return ResultGenerator.genErrorResult(Constants.RESULT_CODE_PARAM_ERROR, "缺少必要字段");
        }
        if (dealService.UpdateDeal(deal) == 1) {
            return ResultGenerator.genSuccessResult("更新成功");
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
