package com.shixun.ihome.work.service.serviceImpl;

import com.shixun.ihome.publicservice.mapper.ITimerMapper;
import com.shixun.ihome.publicservice.pojo.ITimer;
import com.shixun.ihome.publicservice.pojo.ITimerExample;
import com.shixun.ihome.publicservice.util.Qutil;
import com.shixun.ihome.work.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TimeServiceImpl implements TimeService {

    @Autowired
    private ITimerMapper iTimerMapper;
    @Override
    public List<ITimer> selectFreeStaff(Date startTimer, Date endTimer) {
        //计算日期获得timer
        long timer = consumTimer(startTimer, endTimer);
        timer = timerLeft(startTimer,timer);
        return iTimerMapper.selectFreeStaff(timer);
    }


    @Override
    public boolean updateTimer(int id, long timer) {
        ITimer iTimer = getITimeByStaffId(id);
        if (iTimer == null){
            throw new RuntimeException("时间表存在空值");
        }
        Date date = new Date();
        long newtimer = timerLeft(iTimer.getUpdateTime(), iTimer.getTimer());
        //开始运算
        if( (newtimer & timer) == 0) {
            newtimer = (newtimer | timer ) & ITimerMapper.MAXTIMER;
        }else{
            throw new RuntimeException("员工在该时间段已被占用");
        }
        //更新时间表
        return updateTime(date,newtimer, id);
    }

    @Override
    public String test(Date date1, Date date2) {
        int result = consumTimer(date1,date2);
        return Integer.toBinaryString(result);
    }

    @Override
    public boolean updateTimerRemove(int id, long timer ) {
        //根据StaffId查询时间表
        ITimer iTimer = getITimeByStaffId(id);
        Date date = new Date();
        long newtimer = timerLeft(iTimer.getUpdateTime(),iTimer.getTimer());
        if ((newtimer & timer) != 0){
            newtimer = (newtimer ^ timer) & ITimerMapper.MAXTIMER;
        }else{
            throw new RuntimeException("该员工所在的时间端是空闲的，请检测数据是否错误");
        }
        //更新时间表
        return updateTime(date,newtimer, id);
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

    //更新时间安排表
    private long timerLeft(Date order, long timer){
        //获取当前时间
        Date now = new Date();
        //获取两天相隔的天数
        int days = Qutil.consumDays(order, now);
        //获取时间表的时间安排
        long itimer = timer ;
        //左移天数
        itimer = itimer << (days * 6);
        return itimer;
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
        //结果前后加1
        timer = ((timer >> 1) | (timer << 1)) & ITimerMapper.DAY;

        return timer;
    }

    @Override
    public boolean addTimer(int staffId) {
        ITimer timer = new ITimer();
        timer.setStaffId(staffId);
        timer.setTimer(0l);
        timer.setUpdateTime(new Date());
        if(iTimerMapper.insertSelective(timer) == 0) {
            throw new RuntimeException("员工时间表插入失败");
        }
        return true;
    }
}
