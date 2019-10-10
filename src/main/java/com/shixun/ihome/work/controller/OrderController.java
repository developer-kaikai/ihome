package com.shixun.ihome.work.controller;

import com.shixun.ihome.json.Result;
import com.shixun.ihome.json.ResultBase;
import com.shixun.ihome.json.ResultType;
import com.shixun.ihome.publicservice.pojo.HourWork;
import com.shixun.ihome.publicservice.pojo.IOrderLong;
import com.shixun.ihome.publicservice.pojo.IStaff;
import com.shixun.ihome.work.service.OrderService;
import com.shixun.ihome.publicservice.pojo.IOrder;
import com.shixun.ihome.work.service.TimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@Api(description = "订单模块")
@RequestMapping("json/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private TimeService timeService;



    @ApiOperation(value = "增加维修订单")
    @ApiImplicitParam(name="order",value ="订单实体类", required = true, dataType ="IOrder")
    @PostMapping("addIOrder")
    @ResponseBody
    public Object addorder(@RequestBody IOrder order){
        IOrder order1 = orderService.addOrderRecord(order, "乔哥");
        if(order1 != null){
            return "订单插入成功";
        }
        return "订单插入失败";
    }


    @ApiOperation(value="添加长期工的订单")
    @ApiImplicitParam(name="orderLong", value = "长期工订单实体类", dataType = "IOrderLong")
    @PostMapping("addLongOrder")
    @ResponseBody
    public ResultBase addLongOrder(@RequestBody IOrderLong orderLong) {
        IOrder order1 = orderService.addOrderRecord(orderLong.getOrder(), "乔哥");
        if(order1 == null) {
            return new ResultBase(400, "订单数据存在错误");
        }
        orderLong.setId(null);
        orderLong.setOrderId(order1.getId());
        if(orderService.addOrderLong(orderLong)){
            return new ResultBase(200, "插入成功");
        }
        return new ResultBase(400, "插入失败");

    }

    @ApiOperation(value = "取消维修订单")
    @RequestMapping(value = "/cancelOrder",method = RequestMethod.POST)
    @ResponseBody
    public Boolean cancelOrder(int id){
         boolean success=orderService.cancelOrder(id);
         return success;

    }

    @ApiOperation(value = "填写维修详情")
    @RequestMapping(value="/addDetail",method=RequestMethod.POST)
    @ResponseBody
    public Boolean addOrderNew(int id,String odescribe,String sovle,Double price){
           boolean success=orderService.addDetail(id,odescribe,sovle,price);
           return true;
    }


    @ApiOperation(value ="订单评价")
    @RequestMapping(value="/addEvaluate",method = RequestMethod.POST)
    @ResponseBody
    public Boolean addEvaluate(int id, int quality_valuation, int attitude_valuation, String describe){
        boolean success=orderService.addEvaluate(id,quality_valuation,attitude_valuation,describe);
        return true;
    }

    @ApiOperation(value ="查看所有订单")
    @RequestMapping(value="/listAll",method = RequestMethod.GET)
    @ResponseBody
    public void orderAll(HttpServletResponse response)throws IOException {

        List<IOrder> orderList=orderService.listAll();


        response.setContentType("application/json;charset=utf-8");
        String json ;
        json = Result.build(ResultType.Success).appendData("orderList", orderList).convertIntoJSON();

        response.getWriter().write(json);

    }

    @ApiOperation(value="安排钟点工")
    @ApiImplicitParam(name="hourWork", value = "钟点工中间类", dataType = "HourWork")
    @ResponseBody
    @PostMapping(value = "plantHourworkStaff")
    public ResultBase plantHourworkStaff(@RequestBody HourWork hourWork){
        //为订单分配员工
        if (hourWork != null){
            if(hourWork.getStaffs() != null){
                try{
                    for (IStaff staff: hourWork.getStaffs()
                    ) {
                        //插入到订单员工表中
                        orderService.addStaffForOrder(hourWork.getOrder(), staff);
                        //更新员工的时间表
                        timeService.updateTimer(staff.getId(), hourWork.getTimer());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    return new ResultBase(400, "员工安排出现问题");
                }
                return new ResultBase(200, "员工安排成功");
            }else{
                return new ResultBase(400, "员工选择不能为空");
            }
        }
        return new ResultBase(400, "数据存在问题");
    }

    @ApiOperation(value ="删除订单")
    @RequestMapping(value="/deleteOrder",method = RequestMethod.GET)
    @ResponseBody
    public void deleteorder(int id){
        boolean success=orderService.deleteOrder(id);
    }

    @ApiOperation(value ="高级查询订单")
    @RequestMapping(value="/listByType",method = RequestMethod.POST)
    @ResponseBody
    public void orderAllByType(@RequestBody IOrder order, HttpServletResponse response)throws IOException {

        List<IOrder> orderList=orderService.listByCondition(order);
        response.setContentType("application/json;charset=utf-8");
        String json ;
        json = Result.build(ResultType.Success).appendData("orderList", orderList).convertIntoJSON();
        response.getWriter().write(json);
    }
}
