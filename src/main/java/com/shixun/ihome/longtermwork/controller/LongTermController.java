package com.shixun.ihome.longtermwork.controller;

import com.shixun.ihome.longtermwork.service.LongTermService;
import com.shixun.ihome.publicservice.pojo.IOrder;
import com.shixun.ihome.publicservice.pojo.IOrderLong;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Api(description = "长期工订单模块")
@RequestMapping("json/longTermOrder")
public class LongTermController {
    @Autowired
    private LongTermService longTermService;

    @ApiOperation(value = "添加长期工订单")
    @ApiImplicitParam(name = "order",value="订单实体类",required = true,dataType = "IOrder")
    @PostMapping("addOrder")
    @ResponseBody
    public Boolean addOrder(@RequestBody IOrder order){
        IOrderLong orderLong=new  IOrderLong();
        boolean result=longTermService.addOrder(order);
        return  result;
    }
    @ApiOperation(value = "取消维修订单")
    @RequestMapping(value = "/cancelOrder",method = RequestMethod.POST)
    @ResponseBody
    public Boolean cancelOrder(int id){
        boolean result=longTermService.cancelOrder(id);
        return result;
    }
    @ApiOperation(value = "确认维修订单")
    @RequestMapping(value = "/confirmOrder",method = RequestMethod.POST)
    @ResponseBody
    public Boolean confirmOrder(int id){
        boolean result=longTermService.confirmOrder(id);
        return result;
    }
}
