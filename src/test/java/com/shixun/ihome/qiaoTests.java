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
    @Autowired
    private StaffService staffService;

    @Test
    public void test1(){
        //'4', '1', '1', '1', '2019-10-09 09:43:58', '1', '2019-10-09 09:43:58', '2019-10-09 13:43:58', '0', '注释', '1', '04:00:00'
        OrderTimer orderTimer = new OrderTimer();

        IOrder order = orderService.getOrder(4);
        orderTimer.setOrder( order);
        orderTimer.setDays(1);
    }

    @Test
    public void test2() {
        IStaff iStaff  = new IStaff();
        iStaff.setStatus(0);
        iStaff.setSex(0);
        iStaff.setName("你老爸");
        iStaff.setWechatId(1);
        iStaff.setQualification("asdasdasd");
        iStaff.setDetailtypeId(3);
        iStaff.setPhone("asdasdasdads");
        iStaff.setIdCard("asdadsad");
        iStaff.setHealth("asdasdasd");
        IStaff staff = staffService.addStaffRecord(iStaff, "qiaoge");
        System.out.println(iStaff);
        System.out.println(staff);
    }
}
