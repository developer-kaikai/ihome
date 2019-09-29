package com.shixun.ihome;

import com.shixun.ihome.maintenance.service.TimerService;
import com.shixun.ihome.publicservice.pojo.ITimer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class qiaoTests {

    @Autowired
    private TimerService timerService;


    @Test
    public void selectFreeStaff(){
        List<ITimer> timer = timerService.selectFreeStaff(16383);
        System.out.println(timer);
        int i = 0;
        for (ITimer t: timer
             ) {
            System.out.println("no:" + i);
            System.out.println(t.getId());
            System.out.println(t.getTimer());
            System.out.println(t.getUpdateTime());
            System.out.println(t.getStaffId());
        }
    }
    @Test
    public void selectFreeStaffForStaffId(){
        List<Integer> t = timerService.selectFreeStaffForStaffId(16383);
        for (int i: t){
            System.out.println(i);
        }

    }
}
