package com.shixun.ihome.work.service.serviceImpl;

import com.shixun.ihome.publicservice.mapper.ITimerMapper;
import com.shixun.ihome.publicservice.pojo.ITimer;
import com.shixun.ihome.publicservice.pojo.ITimerExample;
import com.shixun.ihome.publicservice.util.Qutil;
import com.shixun.ihome.work.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TimeServiceImpl implements TimeService {

    @Autowired
    private ITimerMapper iTimerMapper;
    @Override
    public List<ITimer> selectFreeStaff(int timer) {
        return iTimerMapper.selectFreeStaff(timer);
    }


    @Override
    public boolean updateTimer(int id, int timer) {
        ITimer iTimer = getITimeByStaffId(id);
        if (iTimer == null){
            throw new RuntimeException("时间表存在空值");
        }
        Date date = new Date();
        int newtimer = timerLeft(iTimer, date);
        //开始运算
        if( (newtimer & timer) == 0) {
            newtimer = (newtimer | timer ) & 16383;
        }else{
            throw new RuntimeException("员工在该时间段已被占用");
        }
        //更新时间表
        return updateTime(date,newtimer, id);
    }


    @Override
    public boolean updateTimerRemove(int id, int timer ) {
        //根据StaffId查询时间表
        ITimer iTimer = getITimeByStaffId(id);
        Date date = new Date();
        int newtimer = timerLeft(iTimer,date);
        if ((newtimer & timer) != 0){
            newtimer = (newtimer ^ timer) & 16383;
        }else{
            throw new RuntimeException("该员工所在的时间端是空闲的，请检测数据是否错误");
        }
        //更新时间表
        return updateTime(date,newtimer, id);
    }

    //更新时间表
    private boolean updateTime(Date date, int itimer, int id){
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
    private int timerLeft(ITimer iTimer, Date date){
        //获取员工时间表更新的最后时间
        Date oldDate = iTimer.getUpdateTime();
        //获取两天相隔的天数
        int days = Qutil.consumDays(date, oldDate);
        //获取时间表的时间安排
        int itimer = iTimer.getTimer();
        //左移天数
        itimer = itimer << (days * 2);
        return itimer;
    }

    @Override
    public boolean addTimer(int staffId) {
        ITimer timer = new ITimer();
        timer.setStaffId(staffId);
        timer.setTimer(0);
        timer.setUpdateTime(new Date());
        if(iTimerMapper.insertSelective(timer) == 0) {
            throw new RuntimeException("员工时间表插入失败");
        }
        return true;
    }
}
