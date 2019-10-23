package com.shixun.ihome;


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
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date date1 = sdf.parse("2019-10-24 15:15");
        Date date2 = sdf.parse("2019-10-24 15:5");
        System.out.println(Qutil.assertTimer(date1, date2, Qutil.MINUTE, 15));
    }


}
