package com.shixun.ihome;


import com.shixun.ihome.publicservice.pojo.IOrder;
import com.shixun.ihome.publicservice.pojo.IStaff;
import com.shixun.ihome.publicservice.pojo.ITimer;
import com.shixun.ihome.publicservice.pojo.OrderTimer;
import com.shixun.ihome.work.service.OrderService;
import com.shixun.ihome.work.service.StaffService;
import com.shixun.ihome.work.service.TimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class qiaoTests {

    @Autowired
    private OrderService orderService;
    @Autowired
    private TimeService timeService;

    @Test
    public void test1(){
        //'4', '1', '1', '1', '2019-10-09 09:43:58', '1', '2019-10-09 09:43:58', '2019-10-09 13:43:58', '0', '注释', '1', '04:00:00'
        OrderTimer orderTimer = new OrderTimer();

        IOrder order = orderService.getOrder(4);
        orderTimer.setOrder( order);
        orderTimer.setDays(1);

    }
}
