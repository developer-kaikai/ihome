package com.shixun.ihome.work.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.util.Date;
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
    public Boolean cancelOrder(@RequestBody JSONObject name) {
        int id=name.getInteger("id");
        boolean success = orderService.cancelOrder(id);
        return success;
    }

    @ApiOperation(value = "填写维修详情")
    @RequestMapping(value = "/addDetail", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value = "维修情况订单id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name="describe", value = "维修的情况解释", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name="solve", value = "作出的维修情报解决方案", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name="price", value = "价格", required = true, dataType = "Double", paramType = "query", example = "0.0")
    })
    public Boolean addOrderNew(@RequestBody JSONObject name) {
        int id=name.getInteger("id");
        String describe=name.getString("describe");
        String solve=name.getString("solve");
        Double price=name.getDouble("price");
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
    public Boolean addEvaluate(@RequestBody JSONObject name) {
        int id=name.getInteger("id");
        int quality_valuation=name.getInteger("quality_valuation");
        int attitude_valuation=name.getInteger("attitude_valuation");
        String describe=name.getString("describe");
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
        return ResultBase.success(orderList);


    }

    @ApiOperation(value = "安排钟点工")
    @Transactional
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, dataType = "int", paramType = "query"),
    })
    @PostMapping(value = "plantHourworkStaff")
    public ResultBase plantHourworkStaff(@RequestParam(name = "staffIds[]") List<Integer> staffIds, Integer orderId) {
        //为订单分配员工
        //获取订单
        IOrder order = orderService.getOrder(orderId);
        //循环员工id
        if (!staffIds.isEmpty()){
            for(Integer id: staffIds){
                //插入到订单员工表之中
                orderService.addStaffForOrder(orderId, id);
                //更新员工的时间表
                timeService.updateTimerByOrder(id, order);
            }

            return new ResultBase(200, "员工安排成功");
        }else{
            return new ResultBase(400, "员工id序列不能为空");
        }
    }

    @ApiOperation(value = "移除订单中的某个员工（钟点工）")
    @Transactional
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "staffId", value = "员工id", required = true, dataType = "int", paramType = "query"),
    })
    @PostMapping("removeHourwordStaff")
    public ResultBase removeHourwordStaff(Integer orderId, Integer staffId) {
        IOrder order = orderService.getOrder(orderId);
        if (orderService.removeStaffForOrder(orderId, staffId)) {
            timeService.removeTimerByOrder(staffId, order);
            return new ResultBase(200, "员工移除成功");
        }

        return new ResultBase(400, "员工移除失败");
    }

    @ApiOperation(value = "移除订单中的某个员工（其他服务）")
    @Transactional
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "staffId", value = "员工id", required = true, dataType = "int", paramType = "query"),
    })
    @PostMapping("removeStaffFromOrder")
    public ResultBase removeStaffFromOrder (Integer orderId, Integer staffId) {
        IOrder order = orderService.getOrder(orderId);
        if (orderService.removeStaffForOrder(orderId, staffId)) {
            timeService.removeTimerByOrder(staffId, order);
            staffService.updateStaffStatus(staffId, 0);
            return new ResultBase(200, "员工移除成功");
        }

        return new ResultBase(400, "员工移除失败");
    }

    @ApiOperation(value = "删除订单")
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.GET)
    public void deleteorder(Integer id) {
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
    public ResultBase plantOtherStaffs(Integer orderId, @RequestParam(name = "staffIds") List<Integer> staffIds, Integer timer) {
        //为订单分配员工
        //获取订单
        IOrder order = orderService.getOrder(orderId);
        //循环员工id
        if (!staffIds.isEmpty()){
            for(Integer id: staffIds){
                //插入到订单员工表之中
                orderService.addStaffForOrder(orderId, id);
                //更新员工的时间表
                timeService.updateTimerByOrder(id, order);
                //更新员工的状态(为服务中2)
                staffService.updateStaffStatus(id, 2);
            }

            return new ResultBase(200, "员工安排成功");
        }else{
            return new ResultBase(400, "员工id序列不能为空");
        }
    }


    @ApiOperation("订单完成")
    @ApiImplicitParam(name="orderId", value = "订单编号", dataType = "int", paramType = "query", required = true)
    @GetMapping(value = "finshOrder")
    public ResultBase finshOrder(Integer orderId){
        //检测时间，订单是否是在服务时间结束后完成
        IOrder order = orderService.getOrder(orderId);
        //检测是否存在订单
        if (order == null){
            return new ResultBase(400, "该订单不存在");
        }
        Date date = new Date();
        Date finshDate = order.getFinalyTime();
        Boolean finish = (date.getTime() - finshDate.getTime()) > 0;
        //如果确定是在服务结束后点击订单完成的
        if (finish){
            //修改订单的状态为完成 4
            if(orderService.updateOrderState(orderId, 4)){
                return new ResultBase(200, "订单完成");
            }


        }else {
            return new ResultBase(400, "请不要在服务时间尚未结束时点击订单完成");
        }
        return new ResultBase(400, "出现未知问题");
    }
    @ApiOperation("分配长期工")
    @ResponseBody
    @PostMapping("/longTermStaffs")
    public ResultBase longTermStaffs(@RequestBody JSONObject order) {
        //为订单分配员工,从前端获取一个json对象
        /*
        * 参考参数
        * {"orderId":1,"staffIds":[]}
        * */
        //循环员工id
        int orderId=order.getInteger("orderId");
        JSONArray staffIds=order.getJSONArray("staffIds");
        List<Integer> listStaffIds=(List)staffIds;

        if (!staffIds.isEmpty()){
            for(Integer id: listStaffIds){
                //插入到订单员工表之中
                orderService.addStaffForOrder(orderId, id);

                //更新员工的状态(为服务中2)
                staffService.updateStaffStatus(id, 2);
            }

            return new ResultBase(200, "员工安排成功");
        }else{
            return new ResultBase(400, "员工id序列不能为空");
        }
    }

}
