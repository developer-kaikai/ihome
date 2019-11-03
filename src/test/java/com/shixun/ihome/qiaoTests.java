package com.shixun.ihome;


import com.alibaba.fastjson.JSONObject;
import com.shixun.ihome.publicservice.mapper.ITimerMapper;
import com.shixun.ihome.publicservice.pojo.*;
import com.shixun.ihome.publicservice.util.Qutil;
import com.shixun.ihome.work.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.Min;
import java.sql.SQLOutput;
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
    @Autowired
    private ITimerMapper iTimerMapper;
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
        timeService.removeTimerByOrder(1, order,2);
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


    @Test
    public void testf(){
        Object  o1 = "asd";
        Object o2 = null;
        String s1 = o1 + "";
        String s2 = o2 + "";
        System.out.println(s2.equals("null"));
        System.out.println(s1);
        System.out.println(s2);
    }


    @Test
    public void testT() throws Exception{
        String time = "2019-11-02 18:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = sdf.parse(time);
        System.out.println(Qutil.getTimer(date));
        System.out.println(Qutil.before(date));
    }

    @Test
    public void test5(){
        IOrder order = orderService.getOrder(94);
        long timer2 = consumTimer(order.getStartTime(), order.getFinalyTime());
        System.out.println(timer2);
        timer2 = worktimer(timer2);
        System.out.println(timer2);
        timer2 = timer2 << ((timerLeft(order.getStartTime())  ) * 6);
        System.out.println(timer2);
        System.out.println(~timer2);
        System.out.println(Long.toBinaryString(~timer2));
    }

    @Test
    public void test6(){
        IOrder order = orderService.getOrder(94);
        Date now = new Date();
        int t =  63 << (( Qutil.consumDays(order.getStartTime(), now)) * 6);
        System.out.println(t);
    }



    //更新时间表
    private boolean updateTime(Date date, long itimer, int id){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("updateTime", date);
        params.put("timer", itimer);
        params.put("staffId", id);
        if(iTimerMapper.updateStaffTime(params) == 0){
            throw new RuntimeException("时间表更新失败");
        }
        return true;
    }

    //根据员工Id获取该员工的时间表
    private ITimer getITimeByStaffId(int staffId){
        ITimerExample iTimerExample = new ITimerExample();
        ITimerExample.Criteria criteria = iTimerExample.createCriteria();
        criteria.andStaffIdEqualTo(staffId);
        ITimer iTimer = iTimerMapper.selectByExample(iTimerExample).get(0);
        return iTimer;
    }

    //安排工作时，下一格也默认标识为工作
    private long worktimer(long timer){
        timer = (((timer << 1 )| timer) & ITimerMapper.DAY);
        return timer;
    }

    //更新时间安排表
    private int timerLeft(Date order){
        //获取当前时间
        order = removeTime(order);
        Date now = removeTime(new Date());
        //获取两天相隔的天数
        int days = Qutil.consumDays(order, now);
        //获取时间表的时间安排
        return days;
    }

    //削去时间部分
    private Date removeTime(Date d){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date nd = null;
        try {
            nd = sdf.parse(sdf.format(d));
        }catch (Exception e){
            throw new RuntimeException("取出日期失败");
        }
        return nd;
    }

    //计算日期 测试完成
    private int consumDate(Date d){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        //获取时间,并且处理
        int hour = calendar.get(Calendar.HOUR_OF_DAY) - 9;
        int minute = calendar.get(Calendar.MINUTE);
        if (minute > 0){
            hour += 1;
        }
        int timer = 0;
        switch (hour) {
            case -1:
            case 0:
            case 1:
                timer = 1;
                break;
            case 2:
            case 3:
                timer = 2;
                break;
            case 4:
            case 5:
                timer = 4;
                break;
            case 6:
            case 7:
                timer = 8;
                break;
            case 8:
            case 9:
                timer = 16;
                break;
            case 10:
            case 11:
                timer = 32;
                break;
            default: {
                throw new RuntimeException("请检测时间是否存在问题");
            }
        }
        return timer;
    }


    //将两个结果中间填一
    private int consumTwoTimer(int startTimer, int endTimer){
        return endTimer - startTimer + endTimer;
    }

    private int consumTimer(Date startTime, Date endTime){
        int startTimer = consumDate(startTime);
        int endTimer = consumDate(endTime);

        int timer = consumTwoTimer(startTimer,endTimer);

        return timer;
    }

    //时间前后加一
    private long addTailAndHeadOne(long timer){
        timer = ((timer >> 1) | (timer << 1)) & ITimerMapper.DAY;
        return timer;
    }



}
