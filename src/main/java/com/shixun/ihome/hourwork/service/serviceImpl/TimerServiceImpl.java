package com.shixun.ihome.hourwork.service.serviceImpl;

import com.shixun.ihome.hourwork.service.TimerService;
import com.shixun.ihome.publicservice.mapper.ITimerMapper;
import com.shixun.ihome.publicservice.pojo.ITimer;
import com.shixun.ihome.publicservice.util.Qutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TimerServiceImpl implements TimerService {

    @Autowired
    private ITimerMapper iTimerMapper;
    @Override
    public List<ITimer> selectFreeStaff(int timer) {
        return iTimerMapper.selectFreeStaff(timer);
    }


    @Override
    public int updateTimer(int id, String timer) {
        ITimer iTimer = iTimerMapper.selectByPrimaryKey(id);
        //获取更新天数
        Date oldDate = iTimer.getUpdateTime();
        Date newDate =new Date();
        int days = Qutil.consumDays(newDate, oldDate);
        int itimer = iTimer.getTimer();
        //左移天数
        itimer = itimer << (days * 2);
        //修改时间表
        int result = Qutil.string2timer(timer);
        int time = iTimer.getTimer();
        //开始运算
        if( (itimer & result) == 0) {
            itimer = (itimer | result ) & 16383;
        }else{
           return 0;
        }
        iTimer.setTimer(itimer);
        iTimer.setUpdateTime(newDate);
        return iTimerMapper.updateByPrimaryKeySelective(iTimer);
    }
}
