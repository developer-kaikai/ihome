package com.shixun.ihome;


import com.alibaba.fastjson.JSONObject;
import com.shixun.ihome.publicservice.pojo.*;
import com.shixun.ihome.publicservice.util.Qutil;
import com.shixun.ihome.work.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.Min;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class qiaoTests {

    @Autowired
    private OrderService orderService;
    @Autowired
    private TimeService timeService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private RedisTimerService redisTimerService;
    @Autowired
    private UserService userService;

    @Test
    public void test1() throws Exception{

//        '9', '1', '1', '1', '2019-10-24 08:00:00', '1000', '2019-10-23 08:47:09', '2019-10-24 10:00:00', '0', '注释'
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        IOrder order = new IOrder();
        order.setId(9);
        order.setUserId(1);
        order.setUseraddressId(1);
        order.setDetailtypeId(1);
        order.setStartTime((sdf.parse("2019-10-24 08:00:0")));
        order.setPrice(1000.0);
        order.setOrderTime((sdf.parse("2019-10-23 08:47:09")));
        order.setFinalyTime(sdf.parse("2019-10-24 10:00:00"));
        order.setState(3);
        order.setComm("asdasd");

        timeService.updateTimerByOrder(1,order,2);
    }

    @Test
    public void test2()throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        IOrder order = new IOrder();
        order.setId(9);
        order.setUserId(1);
        order.setUseraddressId(1);
        order.setDetailtypeId(1);
        order.setStartTime((sdf.parse("2019-10-24 08:00:0")));
        order.setPrice(1000.0);
        order.setOrderTime((sdf.parse("2019-10-23 08:47:09")));
        order.setFinalyTime(sdf.parse("2019-10-24 10:00:00"));
        order.setState(3);
        order.setComm("asdasd");
        timeService.removeTimerByOrder(1, order, 2);
    }


    @Test
    public void test3(){
        JSONObject json = new JSONObject();
        fuck(json);
        String fuck = json.getString("fuck");
        System.out.println(fuck);
    }


    private void fuck(JSONObject json){
        json.put("fuck", "fuck");
    }


}
