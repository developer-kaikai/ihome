package com.shixun.ihome;

import com.shixun.ihome.maintenance.service.StaffService;
import com.shixun.ihome.maintenance.service.TimerService;
import com.shixun.ihome.publicservice.pojo.IStaff;
import com.shixun.ihome.publicservice.pojo.ITimer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class qiaoTests {

    @Autowired
    private TimerService timerService;

    @Autowired
    private StaffService staffService;


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
        Map<String,Object> istaff = new HashMap<String,Object>();
        istaff.put("id", 1);
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
}
