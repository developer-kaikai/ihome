package com.shixun.ihome.work.controller;

import com.alibaba.fastjson.JSONArray;
import com.shixun.ihome.json.ResultBase;
import com.shixun.ihome.work.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "杂项")
@RestController
@RequestMapping("/json/other")
public class OtherController {
    @Autowired
    private UserService userService;

    @ApiOperation("根据用户手机号获取用户的微信id（添加员工使用）")
    @GetMapping("getWeixinIdFromUserPhone/{phone}")
    @ApiImplicitParam(name = "phone" ,value="11111111111", required = true, paramType = "path", dataTypeClass = String.class)
    public ResultBase getWeixinIdFromUserPhone(@PathVariable String phone){
        JSONArray arr = userService.getWeiXinId(phone + "%");
        return ResultBase.success(arr);
    }

}
