package com.shixun.ihome.work.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.shixun.ihome.config.ApiJsonObject;
import com.shixun.ihome.config.ApiJsonProperty;
import com.shixun.ihome.json.Result;
import com.shixun.ihome.json.ResultBase;
import com.shixun.ihome.json.ResultType;
import com.shixun.ihome.publicservice.pojo.*;
import com.shixun.ihome.publicservice.util.Qutil;
import com.shixun.ihome.work.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    @Autowired
    private EvaluateService evaluateService;
    @Autowired
    private UserService userService;
    @Autowired
    private ToolService toolService;
    @Autowired
    private DetailTypeService detailTypeService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ServiceTimerService serviceTimerService;
    @Autowired
    private ServicetypeService servicetypeService;

    @ApiOperation(value="双向确认")
    @RequestMapping(value="/updateOrderState",method = RequestMethod.POST)
    public void updateOrder(@RequestBody JSONObject name){
        int orderid=name.getInteger("orderid");

        Boolean success=orderService.updateOrderState(orderid);


    }


    @ApiOperation(value = "用户和员工模糊查找订单")
    @RequestMapping(value = "/orderBytypename", method = RequestMethod.POST)
    public void orderAllByTy(@RequestBody JSONObject name, HttpServletResponse response) throws IOException {
        String typename=name.getString("typename");
        int userid=name.getInteger("userid");
        int temp=name.getInteger("temp");
        List<IOrder> orders =new ArrayList<>();
        /*员工*/
        if(temp==1){
            orders=orderService.listbystaffTypename(userid,typename);
        }else{
         orders=orderService.listbyuserTypename(userid,typename);}

        response.setContentType("application/json;charset=utf-8");
        String json;
        json = Result.build(ResultType.Success).appendData("orders", orders).convertIntoJSON();
        response.getWriter().write(json);
    }

    @ApiOperation(value = "用户查看订单评价")
    @RequestMapping(value = "/evlistByid", method = RequestMethod.POST)
    public void evlistByid(@RequestBody JSONObject name, HttpServletResponse response) throws IOException {

        int userid=name.getInteger("userid");
        int id=name.getInteger("temp");
        List<IEvaluate> iEvaluateList = new ArrayList<>();
        //员工
        if(id==1){

        iEvaluateList=evaluateService.listbystaff(userid);
        }else{
           iEvaluateList=evaluateService.listAll(userid);
        }
        response.setContentType("application/json;charset=utf-8");
        String json;
        json = Result.build(ResultType.Success).appendData("iEvaluateList", iEvaluateList).convertIntoJSON();
        response.getWriter().write(json);
    }

    @ApiOperation(value = "用户或员工查看订单")
    @RequestMapping(value = "/orderlistByid", method = RequestMethod.POST)
    public void orderlistbyid(@RequestBody JSONObject name, HttpServletResponse response) throws IOException {

        int userid=name.getInteger("userid");
        int id=name.getInteger("temp");
        int state=name.getInteger("state");
        List<IOrder> iOrderList = new ArrayList<>();
        //员工
        if(id==1){
                iOrderList = orderService.listbystaffidtwo(userid,state);
        }else{
                iOrderList = orderService.listbyuserid(userid, state);
        }
        response.setContentType("application/json;charset=utf-8");
        String json;
        json = Result.build(ResultType.Success).appendData("iOrderList", iOrderList).convertIntoJSON();
        response.getWriter().write(json);
    }

    @ApiOperation(value = "添加订单")
    @PostMapping("addOrder")
    @Transactional
    public ResultBase addOrder(@ApiJsonObject(name = "params", value = {
            @ApiJsonProperty(key = "detailTypeId", example = "1", description = "详细服务类型id"),
            @ApiJsonProperty(key = "userAddressId", example = "1", description = "用户地址id"),
            @ApiJsonProperty(key = "price", example = "0.0", description = "价格"),
            @ApiJsonProperty(key = "comm", example = "注释", description = "注释"),
            @ApiJsonProperty(key = "date", example = "2019年10月24日 8:00|2019年10月24日 10:00"),
            @ApiJsonProperty(key = "userId", example = "1", description = "用户id测试用")
    })@RequestBody JSONObject params){

        Integer userId = params.getInteger("userId");
        Integer detailTypeId = params.getInteger("detailTypeId");
        System.out.println(params);
        if (detailTypeId == null){
            return ResultBase.fail("订单缺少详细服务类型");
        }
        //获取其用户地址id
        Integer addressId = params.getInteger("userAddressId");
        if (addressId == null){
            return ResultBase.fail("订单缺少用户地址");
        }
        //获取订单价格
        Double price = params.getDouble("price");
        if (price == null){
            return ResultBase.fail("订单缺少价格");
        }
        //获取订单的注释
        String comm = params.getString("comm");
        if (comm == null){
            return ResultBase.fail("订单缺少注释");
        }
        //获取开始和结束时间
        String date = params.getString("date");
        if (date == null){
            return ResultBase.fail("订单缺少日期");
        }
        IOrder order = new IOrder();
        order.setState(0);
        order.setUserId(userId);
        order.setDetailtypeId(detailTypeId);
        order.setUseraddressId(addressId);
        order.setPrice(price);
        order.setComm(comm);
        //处理订单的日期和时间 开始时间|结束时间
        String [] s = date.split("\\|");
        for (String s1 :
                s) {
            System.out.println(s1);
        }
        System.out.println(s.length);
        if (s.length > 2){
            return ResultBase.fail("请检测时间的格式是否正确");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm");
        try {
            Date startTimer = sdf.parse(s[0]);
            order.setStartTime(startTimer);
            if (s.length == 2){
                Date endTimer = sdf.parse(s[1]);
                order.setFinalyTime(endTimer);
            }
        }catch (Exception e){
            return ResultBase.fail("日期格式存在问题");
        }
        order.setOrderTime(new Date());

        String openId = userService.getOpenId(userId);
        if (orderService.addOrderRecord(order, openId)){
            //获取服务id
            Integer serviceId = servicetypeService.getServiceType(order.getDetailtypeId());
            IServiceTimer serviceTimer = serviceTimerService.getOne(serviceId);
            serviceTimerService.changeTimer(serviceTimer,order.getStartTime(), 1);
            return ResultBase.success();
        }
        return ResultBase.fail("添加订单失败");
    }



    @ApiOperation(value = "添加长期工订单详情")
    @Transactional
    @PostMapping("addLongOrderDetail")
    public ResultBase addLongOrderDetail(@ApiJsonObject(name = "params", value = {
            @ApiJsonProperty(key = "orderId", example = "1", description = "订单id"),
            @ApiJsonProperty(key = "staffId", example = "1", description = "员工id"),
            @ApiJsonProperty(key = "salary", example = "0", description = "工资"),
            @ApiJsonProperty(key = "month", example = "1", description = "月份"),
            @ApiJsonProperty(key = "aboutFile", example = "1", description = "相关文件"),})
    @RequestBody JSONObject detail) {
        int orderId=detail.getInteger("orderId");
        int staffId=detail.getInteger("staffId");
        Double salary=detail.getDouble("salary");
        int month=detail.getInteger("month");
        String aboutFile=detail.getString("aboutFile");
        IOrderStaff orderStaff=new IOrderStaff();
        orderStaff.setOrderId(orderId);
        orderStaff.setStaffId(staffId);
        IOrderLong orderLong=new IOrderLong();
        orderLong.setOrderId(orderId);
        orderLong.setSalary(salary);
        orderLong.setMonth(month);
        orderLong.setAboutFile(aboutFile);
        if(orderService.addOrderLongDetail(orderLong,orderStaff)){
            orderService.updateOrderState(orderId,2);
            return new ResultBase(200, "插入成功");

        }
        return new ResultBase(400, "插入失败");

    }
    @ApiOperation(value = "添加长期工的订单")
    @Transactional
    @ApiImplicitParam(name = "orderLong", value = "长期工订单实体类", dataType = "IOrderLong")
    @PostMapping("addLongOrder")
    public ResultBase addLongOrderDetail(@RequestBody IOrderLong orderLong) {
        if( orderService.addOrderRecord(orderLong.getOrder(), "乔哥")){
            return new ResultBase(400,"长期工订单添加失败");
        }
        if (orderService.addOrderLong(orderLong)) {
            return new ResultBase(200, "插入成功");
        }
        return new ResultBase(400, "插入失败");

    }

    @ApiOperation(value = "取消订单")
    @ApiImplicitParam(name = "id", value = "订单id", required = true, paramType = "query", dataType = "int")
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    public String cancelOrder(@ApiJsonObject(name = "name", value = {
            @ApiJsonProperty(key = "id", example = "1", description = "订单id")
    })@RequestBody JSONObject name) {
        Integer id=name.getInteger("id");
        boolean success = orderService.cancelOrder(id);
        if(success==true){
            return "已为您取消订单!";
        }else{
            return "订单已超时，请联系客服进行取消!";
        }
    }

    @ApiOperation(value = "填写维修详情")
    @RequestMapping(value = "/addDetail", method = RequestMethod.POST)
    public Boolean addOrderNew(@ApiJsonObject(name = "name", value = {
            @ApiJsonProperty(key="id", example= "维修情况订单id"),
            @ApiJsonProperty(key="describe", example= "维修的情况解释"),
            @ApiJsonProperty(key="solve", example= "作出的维修情报解决方案"),
            @ApiJsonProperty(key="price", example= "价格")
    })@RequestBody JSONObject name) {
        int id=name.getInteger("id");
        String describe=name.getString("describe");
        String solve=name.getString("solve");
        Double price=name.getDouble("price");
        boolean success = orderService.addDetail(id, describe, solve, price);
        return true;
    }


    @ApiOperation(value = "订单评价")
    @RequestMapping(value = "/addEvaluate", method = RequestMethod.POST)
    public Boolean addEvaluate(@ApiJsonObject(name = "name", value = {
            @ApiJsonProperty(key = "id", example = "1", description = "订单id"),
            @ApiJsonProperty(key = "quality_valuation", example = "服务质量（1-5星）", description = "服务质量"),
            @ApiJsonProperty(key = "attitude_valuation", example = "服务态度（1-5星）", description = "服务态度"),
            @ApiJsonProperty(key = "describe", example = "备注", description = "备注")
    })@RequestBody JSONObject name) {
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


    //最蠢的方法
    @ApiOperation(value ="获取单个详细数据")
    @GetMapping("/getOne/{id}")
    @ApiImplicitParam(name = "id", value = "1", paramType = "path", dataTypeClass = Integer.class, required = true)
    public ResultBase getOne(@PathVariable Integer id){
        //先获取订单数据
        IOrder order = orderService.getOrder(id);
        //获取详细类型数据
        IDetailtype detailtype = detailTypeService.getOne(order.getDetailtypeId());
        order.setDetailtype(detailtype);
        //获取下单用户信息
        IUser user = userService.getOne(order.getUserId());
        order.setUser(user);
        //获取用户地址信息
        IUserDetail userDetail = addressService.getOne(order.getUseraddressId());
        order.setUserDetail(userDetail);
        return ResultBase.success(order);
    }

    @ApiOperation(value = "查看所有订单测试")
    @RequestMapping(value = "/listAllTest", method = RequestMethod.GET)
    public ResultBase orderAllTest() {
        List<IOrder> orderList = orderService.listAll();
        return ResultBase.success(orderList);
    }

    @ApiOperation(value = "获取不同状态的订单")
    @PostMapping("/getOrderByStatus")
    public ResultBase getOrderByStatus(@ApiJsonObject(name = "params", value = {
            @ApiJsonProperty(key =  "pageNum", example = "1"),
            @ApiJsonProperty(key = "pageSize", example = "10"),
            @ApiJsonProperty(key = "status", example = "0")
    })@RequestBody JSONObject params) {
        IOrder order = new IOrder();
        int pageNum = params.getInteger("pageNum");
        int pageSize = params.getInteger("pageSize");
        int status = params.getInteger("status");
        order.setState(status);
        PageInfo<IOrder> pages = orderService.listByConditionPage(order, pageNum, pageSize);
        JSONObject object = new JSONObject(4);
        object.put("list", pages.getList());
        object.put("pageSize", pages.getPageSize());
        object.put("pageNum", pages.getPageNum());
        object.put("total", pages.getTotal());
        return ResultBase.success(object);
    }


    @ApiOperation(value = "移除订单中的某个员工")
    @Transactional
    @PostMapping("removeStaffFromOrder")
    public ResultBase removeStaffFromOrder (@ApiJsonObject(name = "params", value = {
            @ApiJsonProperty(key = "orderId", example = "1"),
            @ApiJsonProperty(key = "staffId", example = "1"),
            @ApiJsonProperty(key = "serviceId", example = "1")
    })@RequestBody JSONObject params) {
        Integer orderId = params.getInteger("orderId");
        Integer staffId = params.getInteger("staffId");
        Integer serviceId = params.getInteger("serviceId");
        IOrder order = orderService.getOrder(orderId);
        if (orderService.removeStaffForOrder(orderId, staffId)) {
            timeService.removeTimerByOrder(staffId, order,serviceId);
            return new ResultBase(200, "员工移除成功");
        }

        return new ResultBase(400, "员工移除失败");
    }


    @ApiOperation(value = "删除订单")
    @GetMapping("/deleteOrder/{id}")
    @ApiImplicitParam(name = "id", value = "1", paramType = "path", dataTypeClass = Integer.class, required = true)
    public ResultBase deleteorder(@PathVariable Integer id) {
        boolean success = orderService.deleteOrder(id);
        if (success){
            return ResultBase.success();
        }else {
            return ResultBase.fail("删除订单失败");
        }
    }

    @ApiOperation(value = "查询长期工订单")
    @RequestMapping(value = "/listByType", method = RequestMethod.POST)
    public void orderAllByType(@ApiJsonObject(name = "tool", value = {
            @ApiJsonProperty(key = "state", example = "0", description = "状态"),
            @ApiJsonProperty(key = "pageNum", example = "0", description = ""),
            @ApiJsonProperty(key = "pageSize", example = "0", description = ""),
    })@RequestBody JSONObject order, HttpServletResponse response) throws IOException {
        int state=order.getInteger("state");
        int pageNum = order.getInteger("pageNum");
        int pageSize = order.getInteger("pageSize");
        IOrder iOrder=new IOrder();
        iOrder.setState(state);
        PageInfo<IOrder> pages = orderService.listByCondition(iOrder,pageNum,pageSize);
        response.setContentType("application/json;charset=utf-8");
        String json;
        json = Result.build(ResultType.Success).appendData("orderList", pages).convertIntoJSON();
        response.getWriter().write(json);
    }


    @ApiOperation(value = "员工安排")
    @Transactional
    @PostMapping(value = "/plantOtherStaffs")
    public ResultBase plantOtherStaffs(@ApiJsonObject(name = "params", value = {
            @ApiJsonProperty(key = "orderId", example = "1", description = "订单"),
            @ApiJsonProperty(key = "staffIds", example = "[1,2,3,4,5]", description = "员工Id"),
            @ApiJsonProperty(key = "serviceId", example = "1", description = "服务大类id"),
    })@RequestBody JSONObject params ) {
//        Integer orderId, @RequestParam(name = "staffIds") List<Integer> staffIds, Integer timer
        //为订单分配员工
        //获取订单
        Integer orderId = params.getInteger("orderId");
        JSONArray jsonArray = params.getJSONArray("staffIds");
        Integer serviceId = params.getInteger("serviceId");
        IOrder order = orderService.getOrder(orderId);
        if (Qutil.assertTimer(new Date(), order.getOrderTime(), Qutil.MINUTE, 15)){
            return ResultBase.fail("订单请在15分钟内取消");
        }
        //生成工具
        //循环员工id
        if (!jsonArray.isEmpty()){
            ITool tool = toolService.getOne(order.getDetailtypeId());
            IToolrecord toolrecord = null;
            if(tool != null){
                toolrecord = new IToolrecord();
                toolrecord.setOrderId(orderId);
                toolrecord.setToolId(tool.getId());
                toolrecord.setCount(1);
                toolrecord.setState(0);
            }
            List<Integer> staffIds = jsonArray.toJavaList(Integer.class);
            for(Integer id: staffIds){
                //插入到订单员工表之中
                orderService.addStaffForOrder(orderId, id);
                //更新员工的时间表
                timeService.updateTimerByOrder(id, order, serviceId);
                //更新员工的状态(为服务中2)
                staffService.updateStaffStatus(id, 2, 0);
                //添加工具记录
                if(toolrecord != null){
                    toolrecord.setStaffId(id);
                    toolService.addToolrecord(toolrecord);
                }

            }
            //更新订单状态
            orderService.updateOrderState(orderId, 2);

            return ResultBase.success();
        }else{
            return ResultBase.fail("员工序列id不能为空");
        }
    }


    @ApiOperation("订单完成")
    @ApiImplicitParam(name="orderId", value = "订单编号", dataType = "int", paramType = "query", required = true)
    @PostMapping(value = "finshOrder")
    public ResultBase finshOrder(@ApiJsonObject (name = "params", value = {
            @ApiJsonProperty(key = "orderId", example = "1", description = "订单Id")
    })@RequestBody JSONObject params){
        //检测时间，订单是否是在服务时间结束后完成
        Integer orderId = params.getInteger( "orderId");
        IOrder order = orderService.getOrder(orderId);
        //检测是否存在订单
        if (order == null){
            return ResultBase.fail("订单不存在");
        }
        Date date = new Date();
        Date finshDate = order.getFinalyTime();
        Boolean finish = (date.getTime() - finshDate.getTime()) > 0;
        //如果确定是在服务结束后点击订单完成的
        if (finish){
            //修改订单的状态为完成 4
            List<IStaff> staffs = staffService.selectStaffForOrder(orderId);
            for (IStaff staff : staffs) {
                staffService.updateStaffStatus(staff.getId(), 0, 2);
            }
            if(orderService.updateOrderState(orderId, 4)){
                return ResultBase.success();
            }


        }else {
            return ResultBase.fail("请不要在订单还没完成前，点击完成");
        }
        return ResultBase.fail("出现未知问题");
    }


    @ApiOperation(value = "订单查询")
    @PostMapping("/getOrderDataByCondition")
    public ResultBase getOrderDataByCondition(@ApiJsonObject(name = "params", value = {
           @ApiJsonProperty(key = "state", example = "0", description = "订单状态"),
            @ApiJsonProperty(key = "pageSize", example = "10", description = "一页显示的数据量"),
            @ApiJsonProperty(key = "pageNum", example = "1", description = "页数")
    })@RequestBody JSONObject params){
        Integer pageSize = params.getInteger("pageSize");
        Integer pageNum = params.getInteger("pageNum");
        IOrder order = params.toJavaObject(IOrder.class);
        PageInfo<IOrder> pages = orderService.selectByCondition(order, pageNum, pageSize);
        JSONObject data = new JSONObject();
        JSONObject page = new JSONObject();
        page.put("pageSize", pages.getPageSize());
        page.put("pageNum", pages.getPageNum());
        page.put("total", pages.getTotal());
        data.put("page", page);
        data.put("list", pages.getList());
        return ResultBase.success(data);
    }



    @ApiOperation(value = "获取订单员工")
    @GetMapping("/getOrderStaffs/{orderId}")
    @ApiImplicitParam(name = "orderId", value = "1", dataTypeClass = Integer.class, required = true, paramType = "path")
    public ResultBase getOrderStaffs(@PathVariable Integer orderId){
        List<IStaff> staffs = orderService.selectOrderStaffs(orderId);
        return ResultBase.success(staffs);
    }
//    @ApiOperation(value = "添加长期工-订单记录")
//    @PostMapping("/addLongTermOrderStaff")
//    public ResultBase addLongTermOrderStaff(@ApiJsonObject(name = "params", value = {
//            @ApiJsonProperty(key = "state", example = "0", description = "订单状态"),
//            @ApiJsonProperty(key = "pageSize", example = "10", description = "一页显示的数据量"),
//            @ApiJsonProperty(key = "pageNum", example = "1", description = "页数")
//    })@RequestBody JSONObject params){
//        int pageSize = params.getInteger("pageSize");
//        int pageNum = params.getInteger("pageNum");
//        IOrder order = params.toJavaObject(IOrder.class);
//
//        return ResultBase.success();
//    }
}
