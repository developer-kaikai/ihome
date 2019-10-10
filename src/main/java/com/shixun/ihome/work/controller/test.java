package com.shixun.ihome.work.controller;


import com.shixun.ihome.json.ResultBase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@Api(description = "测试模块")
@RequestMapping("json/test")
public class test {
    @ApiOperation("测试方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", required = true, dataType = "String"),
            @ApiImplicitParam(name="password", required = true, dataType = "String")
    })
    @PostMapping("test1")
    @ResponseBody
    public ResultBase TestMethod(@RequestParam(name = "id") String id, @RequestParam(name="password") String password){
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("id", id);
        data.put("password", password);
        return new ResultBase(200, data);
    }
}
