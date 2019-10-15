package com.shixun.ihome.work.controller;


import com.alibaba.fastjson.JSONObject;
import com.shixun.ihome.json.Result;
import com.shixun.ihome.json.ResultType;
import com.shixun.ihome.publicservice.pojo.ISalary;
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
import java.util.List;
import java.util.Map;

@Controller
@Api(description = "财务模块")
@RequestMapping("json/order")
public class FinanceController {
    @Autowired
    private FinanceService financeService;

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


}
