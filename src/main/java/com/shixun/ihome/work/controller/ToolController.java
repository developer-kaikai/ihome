package com.shixun.ihome.work.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.shixun.ihome.config.ApiJsonObject;
import com.shixun.ihome.config.ApiJsonProperty;
import com.shixun.ihome.json.Result;
import com.shixun.ihome.json.ResultBase;
import com.shixun.ihome.json.ResultType;
import com.shixun.ihome.publicservice.pojo.ITool;
import com.shixun.ihome.publicservice.pojo.IToolrecord;
import com.shixun.ihome.work.service.ToolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@Api(description = "工具模块")
@RequestMapping("json/tool")
public class ToolController {
    @Autowired
    private ToolService toolService;

    @ApiOperation(value = "员工查看工具记录")
    @RequestMapping(value = "/seetool",method = RequestMethod.POST)
    public void selectTool(@RequestBody JSONObject name, HttpServletResponse response) throws IOException {
        int userid=name.getInteger("userid");
        int state=name.getInteger("state");
        List<IToolrecord> iToolrecordList=toolService.listbystaffid(userid,state);
        response.setContentType("application/json;charset=utf-8");
        String json;
        json = Result.build(ResultType.Success).appendData("iToolrecordList",iToolrecordList).convertIntoJSON();
        response.getWriter().write(json);
    }




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
    @RequestMapping(value = "/alltoolrecord",method = RequestMethod.POST)
    public void allToolrecord(@ApiJsonObject(name = "tool", value = {
            @ApiJsonProperty(key = "state", example = "0", description = "状态"),
            @ApiJsonProperty(key = "pageNum", example = "0", description = ""),
            @ApiJsonProperty(key = "pageSize", example = "0", description = ""),
    })@RequestBody JSONObject name, HttpServletResponse response)throws IOException{
        int state=name.getInteger("state");
        int pageNum = name.getInteger("pageNum");
        int pageSize = name.getInteger("pageSize");
        PageInfo<IToolrecord> pages =toolService.allRecord(state,pageNum,pageSize);
        response.setContentType("application/json;charset=utf-8");
        String json ;
        json = Result.build(ResultType.Success).appendData("pages",pages).convertIntoJSON();
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


    @ApiOperation(value = "根据订单id获取工具记录")
    @GetMapping("/getToolRecordsByOrderId/{orderId}")
    @ApiImplicitParam(name = "orderId", value = "1", dataTypeClass = Integer.class, required = true, paramType = "path")
    public ResultBase getToolRecordsByOrderId(@PathVariable Integer orderId){
        List<IToolrecord> list = toolService.selectByOrderId(orderId);
        return ResultBase.success(list);
    }

}
