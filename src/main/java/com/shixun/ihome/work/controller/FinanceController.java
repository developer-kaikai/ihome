package com.shixun.ihome.work.controller;


import com.alibaba.fastjson.JSONObject;
import com.shixun.ihome.config.ApiJsonObject;
import com.shixun.ihome.config.ApiJsonProperty;
import com.shixun.ihome.json.Result;
import com.shixun.ihome.json.ResultBase;
import com.shixun.ihome.json.ResultType;
import com.shixun.ihome.publicservice.pojo.ISalary;
import com.shixun.ihome.test.service.WechatService;
import com.shixun.ihome.work.service.EvaluateService;
import com.shixun.ihome.work.service.FinanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@Api(description = "财务模块")
@RequestMapping("json/order")
public class FinanceController {
    @Autowired
    private FinanceService financeService;
    @Autowired
    private WechatService wechatService;
    @Autowired
    private EvaluateService evaluateService;


    @ApiOperation(value = "员工评价排行")
    @ResponseBody
    @RequestMapping(value = "/staffnews",method = RequestMethod.GET)
    public void listevaluate(HttpServletResponse response)throws IOException {
        List<Map<String,Object>> map=evaluateService.listevaluate();

        response.setContentType("application/json;charset=utf-8");
        String json ;
        json = Result.build(ResultType.Success).appendData("map", map).convertIntoJSON();
        response.getWriter().write(json);
    }

    @ApiOperation(value = "登录测试接口")
    @RequestMapping(value="/longintest",method = RequestMethod.POST)
    public String addtest(@RequestBody JSONObject name){
        String openid=name.getString("openid");
        int a=wechatService.wechatlogin(openid);
        return "ok";
    }

    @ApiOperation(value = "电话测试接口")
    @RequestMapping(value="/longinphone",method = RequestMethod.POST)
    public String addtestrwo(@RequestBody JSONObject name){
        int userid=name.getInteger("userid");
        String success=wechatService.havaphone(userid);
        System.out.println("ok");
        return success;
    }

    @ApiOperation(value = "员工页面数据")
    @ResponseBody
    @RequestMapping(value = "/staffnews",method = RequestMethod.POST)
    public void servicestaff(@RequestBody JSONObject name,HttpServletResponse response)throws IOException {
        int staffid=name.getInteger("staffid");
        Date date=new Date();
        Map map=financeService.listcount(staffid,date);

        response.setContentType("application/json;charset=utf-8");
        String json ;
        json = Result.build(ResultType.Success).appendData("map", map).convertIntoJSON();
        response.getWriter().write(json);
    }


    @ApiOperation(value = "test")
    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public void service(HttpServletResponse response)throws IOException {
        List<Map<String,Object>> map1=financeService.addsalary();

        response.setContentType("application/json;charset=utf-8");
        String json ;
        json = Result.build(ResultType.Success).appendData("map1", map1).convertIntoJSON();
        response.getWriter().write(json);
    }

    @ApiOperation(value = "批量更新工资")
    @ResponseBody
    @RequestMapping(value = "/salarylist",method = RequestMethod.GET)
    public void service1(HttpServletResponse response)throws IOException {
        List<Map<String,Object>> map1=financeService.addsalary();
        List<ISalary> map=financeService.salarynow(map1);
        response.setContentType("application/json;charset=utf-8");
        String json ;
        json = Result.build(ResultType.Success).appendData("map", map).convertIntoJSON();
        response.getWriter().write(json);
    }

    @ApiOperation(value="调整提成和奖金")
    @ResponseBody
    @RequestMapping(value = "/modifySalary",method = RequestMethod.POST)
    public void modify(@RequestBody JSONObject name,HttpServletResponse response)throws IOException{
        System.out.println(name);
        double bonusrate=name.getDouble("bonusrate");
        double rolatyrate=name.getDouble("rolatyrate");
        int id=name.getInteger("typeid");
        System.out.println(id);
        Boolean success=financeService.modifySalary(id,bonusrate,rolatyrate);

    }


    @ApiOperation(value = "test1")
    @ResponseBody
    @RequestMapping(value = "/salarylistBymonth",method = RequestMethod.POST)
    public void mouth(@RequestBody JSONObject name,HttpServletResponse response)throws IOException {
        Date date=name.getDate("date1");
        System.out.println(date);
        List<Map<String,Object>> map1=financeService.addsalary();
        List<ISalary> list=financeService.selectSalaryBymonth(date,map1);
        response.setContentType("application/json;charset=utf-8");
        String json ;
        json = Result.build(ResultType.Success).appendData("list", list).convertIntoJSON();
        response.getWriter().write(json);
    }

    @ApiOperation(value="调整基础工资")
    @ResponseBody
    @RequestMapping(value = "/modifyBaseSalary",method = RequestMethod.POST)
    public ResultBase modifyBaseSalary(@ApiJsonObject(name = "name", value = {
            @ApiJsonProperty(key = "id", example = "1", description = "工资表id"),
            @ApiJsonProperty(key = "basesalary", example = "3000", description = "基础工资")})
        @RequestBody JSONObject name,HttpServletResponse response)throws IOException{

        double basesalary=name.getDouble("basesalary");
        int id=name.getInteger("id");
        //System.out.println(id);
        ISalary iSalary=new ISalary();
        iSalary.setId(id);
        iSalary.setBasesalary(basesalary);
        boolean flag=financeService.modifyBaseSalar(iSalary);
        if (flag){

            return ResultBase.success();
        }
        return ResultBase.fail("修改失败");

    }

}
