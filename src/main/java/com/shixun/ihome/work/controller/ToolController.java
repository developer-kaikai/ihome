package com.shixun.ihome.work.controller;

import com.alibaba.fastjson.JSONObject;
import com.shixun.ihome.json.Result;
import com.shixun.ihome.json.ResultType;
import com.shixun.ihome.publicservice.pojo.ITool;
import com.shixun.ihome.publicservice.pojo.IToolrecord;
import com.shixun.ihome.work.service.ToolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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

    @ApiOperation(value = "增加工具")
    @RequestMapping(value = "/addtool",method = RequestMethod.POST)
    public void addTool(@RequestBody ITool iTool){

        Boolean success=toolService.addTool(iTool);
    }

    @ApiOperation(value = "删除工具")
    @RequestMapping(value = "/deletetool",method = RequestMethod.POST)
    public void deleteTool(@RequestBody JSONObject name){
        int id=name.getInteger("itoolid");
        Boolean success=toolService.deleteTool(id);
    }

    @ApiOperation(value = "查找工具")
    @RequestMapping(value = "/findtool",method = RequestMethod.POST)
    public void findTool(@RequestBody JSONObject name, HttpServletResponse response)throws IOException {
        String itoolname=name.getString("itoolname");
        List<ITool> iToolList=toolService.selectByname(itoolname);
        response.setContentType("application/json;charset=utf-8");
        String json ;
        json = Result.build(ResultType.Success).appendData("iToolList",iToolList).convertIntoJSON();
        response.getWriter().write(json);

    }

    @ApiOperation(value = "修改工具")
    @RequestMapping(value = "/updatetool",method = RequestMethod.POST)
    public void updateTool(@RequestBody ITool iTool){
       Boolean sucess=toolService.updateTool(iTool);
    }

    @ApiOperation(value = "查看所有工具")
    @RequestMapping(value = "/alltool",method = RequestMethod.GET)
    public void selectAllTool(HttpServletResponse response)throws IOException{
        List<ITool> iToolList=toolService.selectAll();
        response.setContentType("application/json;charset=utf-8");
        String json ;
        json = Result.build(ResultType.Success).appendData("iToolList",iToolList).convertIntoJSON();
        response.getWriter().write(json);
    }

    @ApiOperation(value = "查看所有工具记录")
    @RequestMapping(value = "/alltoolrecord",method = RequestMethod.GET)
    public void allToolrecord(HttpServletResponse response)throws IOException{
        List<IToolrecord> iToolrecordList=toolService.allRecord();
        response.setContentType("application/json;charset=utf-8");
        String json ;
        json = Result.build(ResultType.Success).appendData("iToolrecordList",iToolrecordList).convertIntoJSON();
        response.getWriter().write(json);
    }

    @ApiOperation(value = "修改工具记录")
    @RequestMapping(value = "/updatetoolrecord",method = RequestMethod.POST)
    public void updateToolrecor(@RequestBody IToolrecord iToolrecord){
        Boolean sucess=toolService.updateToolrecord(iToolrecord);
    }

    @ApiOperation(value = "删除工具记录")
    @RequestMapping(value = "/deletetoolrecord",method = RequestMethod.POST)
    public void deleteToolcord(@RequestBody JSONObject name){
        int id=name.getInteger("itoolrecordid");
        Boolean success=toolService.deteToolrecord(id);
    }



}
