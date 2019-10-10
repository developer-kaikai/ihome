package com.shixun.ihome;

import com.shixun.ihome.hourwork.service.HourworkStaffService;
import com.shixun.ihome.hourwork.service.HourworkTimerService;
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
    private HourworkTimerService timerService;

    @Autowired
    private HourworkStaffService staffService;


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
        IStaff iStaff = staffService.getOne(1);
        boolean result = staffService.deleteStaff(iStaff,"乔哥233");
        System.out.println(result);
    }

    @Test
    public void insertStaff(){
        IStaff iStaff = new IStaff();
        iStaff.setStatus(0);
        iStaff.setDetailtypeId(1);
        iStaff.setHealth("无");
        iStaff.setIdCard("1124124124");
        iStaff.setName("铁蛋");
        iStaff.setPhone("124125124");
        iStaff.setQualification("无");
        iStaff.setSex(0);
        iStaff.setWechatId(4);
        staffService.addStaff(iStaff, "乔233");
    }

    @Test
    public void updateStaff(){
        IStaff iStaff = new IStaff();
        iStaff.setId(1);
        iStaff.setName("你麻麻");
        IStaff oldrecord = staffService.getOne(1);
        staffService.updateStaff(iStaff,oldrecord,"乔哥");
    }

    @Test
    public void updateTimer(){
        timerService.updateTimer(2, "0000000101");
    }
}
