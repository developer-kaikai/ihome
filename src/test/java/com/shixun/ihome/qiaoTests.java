package com.shixun.ihome;


import com.shixun.ihome.publicservice.pojo.IOrder;
import com.shixun.ihome.publicservice.pojo.IStaff;
import com.shixun.ihome.publicservice.pojo.ITimer;
import com.shixun.ihome.work.service.OrderService;
import com.shixun.ihome.work.service.StaffService;
import com.shixun.ihome.work.service.TimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
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
    @Autowired
    private StaffService staffService;

    @Test
    public void test1() throws ParseException {
        IOrder order = orderService.getOrder(3);
        List<ITimer> staffs = timeService.selectFreeStaff(order.getStartTime(), order.getFinalyTime());
        for (ITimer staff: staffs
             ) {
            System.out.println(staff);
        }
    }

    @Test
    public void test2() {
        System.out.println("----------" + Integer.class.getName());
    }


    @Test
    public void test3() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = sdf.parse(sdf.format(new Date()));
        Date order = sdf.parse("2019-10-20");
        String s = timeService.test(order, now);
        System.out.println(s);
    }

    @Test
    public void test4() {
        IOrder order = orderService.getOrder(3);
        timeService.updateTimerByOrder(2,order);
    }

    @Test
    public void test5(){
    }



}
