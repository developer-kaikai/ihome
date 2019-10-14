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
import java.util.Calendar;
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
    public void test1(){
        int timer1 = 2;
        int timer2 = 16;
        System.out.println(consum(timer1, timer2));
        timer2 = 32;
        timer1 = 1;
        System.out.println(consum(timer1, timer2));
        timer2 = 16;
        timer1 = 16;
        System.out.println(consum(timer1, timer2));
    }


    private String consum(int timer1, int timer2){
        int t = timer2 - timer1 + timer2;
        return Integer.toBinaryString(t);
    }

}
