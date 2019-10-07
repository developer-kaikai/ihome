package com.shixun.ihome.backgroundsystem.controller;

import com.shixun.ihome.backgroundsystem.service.OrderManagementService;
import com.shixun.ihome.json.Result;
import com.shixun.ihome.json.ResultType;
import com.shixun.ihome.publicservice.pojo.IOrder;
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

@Controller
@Api(description = "后台订单管理模块测试")
@RequestMapping("json/OrderManagement")
public class OrderManagementController {
    @Autowired
    private OrderManagementService orderManagementService;


    @ApiOperation(value ="查询所有订单")
    @RequestMapping(value="/listAll",method = RequestMethod.GET)
    @ResponseBody
    public void orderAll(HttpServletResponse response)throws IOException {

        List<IOrder> orderList=orderManagementService.listAll();
        response.setContentType("application/json;charset=utf-8");
        String json ;
        json = Result.build(ResultType.Success).appendData("orderList", orderList).convertIntoJSON();
        response.getWriter().write(json);
    }



    @ApiOperation(value ="删除订单")
    @RequestMapping(value="/deleteOrder",method = RequestMethod.GET)
    @ResponseBody
    public void deleteorder(int id){
        boolean success=orderManagementService.deleteOrder(id);
    }


    @ApiOperation(value ="高级查询订单")
    @RequestMapping(value="/listByType",method = RequestMethod.POST)
    @ResponseBody
    public void orderAllByType(@RequestBody IOrder order, HttpServletResponse response)throws IOException {

        List<IOrder> orderList=orderManagementService.listByCondition(order);
        response.setContentType("application/json;charset=utf-8");
        String json ;
        json = Result.build(ResultType.Success).appendData("orderList", orderList).convertIntoJSON();
        response.getWriter().write(json);
    }

}
