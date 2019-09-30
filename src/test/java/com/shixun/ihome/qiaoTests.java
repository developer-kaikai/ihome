package com.shixun.ihome;

import com.shixun.ihome.hourwork.service.StaffService;
import com.shixun.ihome.hourwork.service.TimerService;
import com.shixun.ihome.publicservice.pojo.IStaff;
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

    @Autowired
    private StaffService staffService;


    @Test
    public void selectFreeStaff(){
        List<ITimer> timer = timerService.selectFreeStaff(0);
        for (ITimer t: timer
             ) {
            System.out.println(t.toString());
        }
    }

    @Test
    public void selectStaffByStatus() {
        List<IStaff> iStaffs =staffService.selectStaffByState(0);
        for (IStaff iStaff : iStaffs) {
            System.out.println(iStaff.toString());
        }
    }
    @Test
    public void string2timer(){
        String timer = "01000000001000sdf"; //14个零一
        //转换为int
        int time = 0;
        try {
            time =  Integer.parseInt(timer, 2);
        }catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(time);
    }
    @Test
    public void selectStaffByServicetypeId () {
        List<IStaff> iStaffs =  staffService.selectStaffByServicetypeId(1);
        for (IStaff i: iStaffs
             ) {
            System.out.println(i.toString());
        }
    }
    @Test
    public void selectStaffs(){
        IStaff istaff = new IStaff();
        List<IStaff> iStaffs = staffService.selectStaffs(istaff);
        for (IStaff i: iStaffs
             ) {
            System.out.println(i.toString());
        }
    }

    @Test
    public void deleteStaff(){
        int result = staffService.deleteStaff(1,"乔哥233");
        System.out.println(result);
    }

    @Test
    public void insertStaff(){
        IStaff iStaff = new IStaff();
        iStaff.setStatus(0);
        iStaff.setDetailtypeId(1);
        iStaff.setHealth("无");
        iStaff.setIdCard("5643468476535");
        iStaff.setName("李四");
        iStaff.setPhone("3465456");
        iStaff.setQualification("无");
        iStaff.setSex(0);
        iStaff.setWechatId(3);
    }

    @Test
    public void updateTimer(){
        timerService.updateTimer(2, "0000000101");
    }
}
