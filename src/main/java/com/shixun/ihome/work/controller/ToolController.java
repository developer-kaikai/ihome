package com.shixun.ihome.work.controller;

import com.alibaba.fastjson.JSONObject;
import com.shixun.ihome.work.service.ToolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(description = "工具模块")
@RequestMapping("json/tool")
public class ToolController {
    @Autowired
    private ToolService toolService;

    @ApiOperation(value = "员工领取工具")
    @RequestMapping(value = "/gettool",method = RequestMethod.POST)
    public void getTool(@RequestBody JSONObject name){
        int orderid=name.getInteger("orderid");
        int staffid=name.getInteger("staffid");
        Boolean success=toolService.receiveTool(orderid,staffid);
    }

    @ApiOperation(value = "员工归还工具")
    @RequestMapping(value = "/returntool",method = RequestMethod.POST)
    public void returnTool(@RequestBody JSONObject name){
        int orderid=name.getInteger("orderid");
        int staffid=name.getInteger("staffid");
        Boolean success=toolService.returnTool(orderid,staffid);
    }

    @ApiOperation(value = "工具损坏")
    @RequestMapping(value = "/damtool",method = RequestMethod.POST)
    public void damTool(@RequestBody JSONObject name){
        int orderid=name.getInteger("orderid");
        int staffid=name.getInteger("staffid");
        Boolean success=toolService.damageTool(orderid,staffid);
    }

}
