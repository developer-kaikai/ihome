package com.shixun.ihome.work.controller;

import com.shixun.ihome.json.Result;
import com.shixun.ihome.json.ResultBase;
import com.shixun.ihome.json.ResultType;
import com.shixun.ihome.publicservice.pojo.IOrderLong;
import com.shixun.ihome.work.service.OrderService;
import com.shixun.ihome.publicservice.pojo.IOrder;
import com.shixun.ihome.work.service.StaffService;
import com.shixun.ihome.work.service.TimeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@Api(description = "订单模块")
@RequestMapping("json/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private TimeService timeService;
    @Autowired
    private StaffService staffService;


    @ApiOperation(value = "增加维修订单")
    @Transactional
    @ApiImplicitParam(name = "order", value = "订单实体类", required = true, dataType = "IOrder")
    @PostMapping("addIOrder")
    public ResultBase addorder(@RequestBody IOrder order) {
        if (orderService.addOrderRecord(order, "乔哥")){
            return new ResultBase(200, "维修单添加添加成功");
        }
        return new ResultBase(400, "维修单添加失败");
    }


    @ApiOperation(value = "添加长期工的订单")
    @Transactional
    @ApiImplicitParam(name = "orderLong", value = "长期工订单实体类", dataType = "IOrderLong")
    @PostMapping("addLongOrder")
    public ResultBase addLongOrder(@RequestBody IOrderLong orderLong) {
        if( orderService.addOrderRecord(orderLong.getOrder(), "乔哥")){
            return new ResultBase(400,"长期工订单添加失败");
        }
        if (orderService.addOrderLong(orderLong)) {
            return new ResultBase(200, "插入成功");
        }
        return new ResultBase(400, "插入失败");

    }

    @ApiOperation(value = "取消维修订单")
    @ApiImplicitParam(name = "id", value = "订单id", required = true, paramType = "query", dataType = "int")
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    public Boolean cancelOrder(int id) {
        boolean success = orderService.cancelOrder(id);
        return success;
    }

    @ApiOperation(value = "填写维修详情")
    @RequestMapping(value = "/addDetail", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value = "维修情况订单id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name="describe", value = "维修的情况解释", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name="solve", value = "作出的维修情报解决方案", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name="price", value = "价格", required = true, dataType = "Double", paramType = "query")
    })
    public Boolean addOrderNew(int id, String describe, String solve, Double price) {
        boolean success = orderService.addDetail(id, describe, solve, price);
        return true;
    }


    @ApiOperation(value = "订单评价")
    @RequestMapping(value = "/addEvaluate", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "quality_valuation", value = "服务质量(1-5星)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "attitude_valuation", value = "服务态度（1-5星）", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "describe", value = "备注", dataType = "String", paramType = "query")
    })
    public Boolean addEvaluate(int id, int quality_valuation, int attitude_valuation, String describe) {
        boolean success = orderService.addEvaluate(id, quality_valuation, attitude_valuation, describe);
        return true;
    }

    @ApiOperation(value = "查看所有订单")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public void orderAll(HttpServletResponse response) throws IOException {

        List<IOrder> orderList = orderService.listAll();


        response.setContentType("application/json;charset=utf-8");
        String json;
        json = Result.build(ResultType.Success).appendData("orderList", orderList).convertIntoJSON();

        response.getWriter().write(json);

    }


    @ApiOperation(value = "查看所有订单测试")
    @RequestMapping(value = "/listAllTest", method = RequestMethod.GET)
    public ResultBase orderAllTest() {

        List<IOrder> orderList = orderService.listAll();
        return new ResultBase(200, orderList);


    }

    @ApiOperation(value = "安排钟点工")
    @Transactional
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "timer", value = "时间表属性（别管，传回来就好)", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping(value = "plantHourworkStaff")
    public ResultBase plantHourworkStaff(@RequestParam(name = "staffIds[]") List<Integer> staffIds, int orderId, int timer) {
        //为订单分配员工
        //获取订单
        IOrder order = orderService.getOrder(orderId);
        //循环员工id
        if (!staffIds.isEmpty()){
            for(int id: staffIds){
                //插入到订单员工表之中
                orderService.addStaffForOrder(orderId, id);
                //更新员工的时间表
                timeService.updateTimer(id, timer);
            }

            return new ResultBase(200, "员工安排成功");
        }else{
            return new ResultBase(400, "员工id序列不能为空");
        }
    }

    @ApiOperation(value = "移除订单中的某个员工（钟点工）")
    @Transactional
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "staffId", value = "员工id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "timer", value = "时间表值（不要管，在查询时，会给予，发回来就好）", required = true, dataType = "Integer", paramType = "query")
    })
    @PostMapping("removeHourwordStaff")
    public ResultBase removeHourwordStaff(int orderId, int staffId, int timer) {
        if (orderService.removeStaffForOrder(orderId, staffId)) {
            timeService.updateTimerRemove(staffId, timer);
            return new ResultBase(200, "员工移除成功");
        }

        return new ResultBase(400, "员工移除失败");
    }

    @ApiOperation(value = "移除订单中的某个员工（其他服务）")
    @Transactional
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "staffId", value = "员工id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "timer", value = "时间表值（不要管，在查询时，会给予，发回来就好）", required = true, dataType = "Integer", paramType = "query")
    })
    @PostMapping("removeStaffFromOrder")
    public ResultBase removeStaffFromOrder (int orderId, int staffId, int timer) {
        if (orderService.removeStaffForOrder(orderId, staffId)) {
            timeService.updateTimerRemove(staffId, timer);
            staffService.updateStaffStatus(staffId, 0);
            return new ResultBase(200, "员工移除成功");
        }

        return new ResultBase(400, "员工移除失败");
    }

    @ApiOperation(value = "删除订单")
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.GET)
    public void deleteorder(int id) {
        boolean success = orderService.deleteOrder(id);
    }

    @ApiOperation(value = "高级查询订单")
    @RequestMapping(value = "/listByType", method = RequestMethod.POST)
    public void orderAllByType(@RequestBody IOrder order, HttpServletResponse response) throws IOException {

        List<IOrder> orderList = orderService.listByCondition(order);
        response.setContentType("application/json;charset=utf-8");
        String json;
        json = Result.build(ResultType.Success).appendData("orderList", orderList).convertIntoJSON();
        response.getWriter().write(json);
    }


    @ApiOperation(value = "其他服务的员工安排")
    @Transactional
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "timer", value = "时间表属性（别管，传回来就好)", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/plantOtherStaffs")
    public ResultBase plantOtherStaffs(int orderId, @RequestParam(name = "staffIds") List<Integer> staffIds, int timer) {
        //为订单分配员工
        //获取订单
        IOrder order = orderService.getOrder(orderId);
        //循环员工id
        if (!staffIds.isEmpty()){
            for(int id: staffIds){
                //插入到订单员工表之中
                orderService.addStaffForOrder(orderId, id);
                //更新员工的时间表
                timeService.updateTimer(id, timer);
                //更新员工的状态(为服务中2)
                staffService.updateStaffStatus(id, 2);
            }

            return new ResultBase(200, "员工安排成功");
        }else{
            return new ResultBase(400, "员工id序列不能为空");
        }
    }


}
