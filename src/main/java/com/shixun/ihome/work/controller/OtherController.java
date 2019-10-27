package com.shixun.ihome.work.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shixun.ihome.config.ApiJsonObject;
import com.shixun.ihome.config.ApiJsonProperty;
import com.shixun.ihome.json.ResultBase;
import com.shixun.ihome.work.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

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

    @ApiOperation(value = "登录测试")
    @PostMapping("/pcLogin")
    public ResultBase pcLogin (@ApiJsonObject (name = "params", value = {
            @ApiJsonProperty(key = "name", example = "user", description = "用户名"),
            @ApiJsonProperty(key = "password", example = "密码", description = "密码")
    })@RequestBody JSONObject params, @ApiIgnore HttpSession session){
        String name = params.getString("name");
        String password = params.getString("password");
        if (password.equals("123456")){
            session.setAttribute("name", name);
            return ResultBase.success();
        }else {
            return ResultBase.fail("密码错误");
        }
    }


    @ApiOperation(value = "获取用户信息测试")
    @GetMapping("/getUserInfo")
    public ResultBase getUserInfo(@ApiIgnore HttpSession session){
        String name = (String) session.getAttribute("name");
        return ResultBase.success(name);
    }

}
