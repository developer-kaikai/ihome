package com.shixun.ihome.work.service.serviceImpl;

import com.shixun.ihome.publicservice.mapper.IServiceTimerMapper;
import com.shixun.ihome.publicservice.pojo.IServiceTimer;
import com.shixun.ihome.publicservice.util.Qutil;
import com.shixun.ihome.work.service.ServiceTimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ServiceTimerServiceImpl implements ServiceTimerService {
    @Autowired
    private IServiceTimerMapper serviceTimerMapper;
    @Override
    public IServiceTimer getOne(int serviceId) {
        //获取时间表
        IServiceTimer iServiceTimer = serviceTimerMapper.selectByPrimaryKey(serviceId);
        Date updateTimer = iServiceTimer.getUpdatetimer();
        Date now = new Date();
        if(!Qutil.assertDate(updateTimer, now)){
            //如果日期不同
            //执行时间表更新
            now = Qutil.removeTimer(now);
            iServiceTimer.setUpdatetimer(now);
            List<Integer> timers = timerSpilt(iServiceTimer.getDate());
            timers = updateTimer(timers,Qutil.consumDays(updateTimer, now), serviceId == 1?6:1);
            iServiceTimer.setDate(toText(timers));
            //更新时间表
            int result = serviceTimerMapper.updateByPrimaryKeySelective(iServiceTimer);

        }
        return  iServiceTimer;
    }

    @Override
    public Long toTimer(IServiceTimer timer) {
        int staffNum = timer.getStaffnum();
        int num = timer.getNum();
        double index = timer.getIndex();
        int result = (staffNum * num * index / timer.getServicelid() == 1? 6:1);
        List<Integer> list = timerSpilt(timer.getDate());
        StringBuffer stringBuffer = new StringBuffer();
        for(int i  = 0; i < list.size(); i++){
            stringBuffer.append(list.get(i) <= result?0:1);
        }
        return Long.parseLong(stringBuffer.toString(), 2);
    }

    @Override
    public boolean changeTimer(IServiceTimer timer, Date date, int type) {
        //计算日期
        Date now = new Date();
        int day = Qutil.consumDays(now , date);
        //计算所占的格
        int s = Qutil.getTimer(date);
        List<Integer> list = timerSpilt(timer.getDate());
        int pos = 1;
        if (timer.getServicelid() == 1){
            pos = 6;
        }
        int index = day * pos + s - 1;
        int value = list.get(index);
        list.set(index, value + type);
        timer.setDate(toText(list));
        serviceTimerMapper.updateByPrimaryKeySelective(timer);
        return true;
    }






    //-----------------------------------------------------------------------------------------------------

    //转换成List<integer>
    private List<Integer> timerSpilt(String text){
        String [] s = text.split("|");
        List<Integer> timers = new ArrayList<>();
        for (String s1 : s) {
            Integer i = Integer.parseInt(s1);
            timers.add(i);
        }
        return timers;
    }
    //将list转String
    private String toText(List<Integer>timers){
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < timers.size(); i++){
            s.append(i);
            s.append('|');
        }
        return s.substring(0, s.length()-1);
    }
    //更新天数
    private List<Integer> updateTimer(List<Integer> timers, int day, int pos){
        List<Integer> newTimers = new ArrayList<>();
        int start = day * pos - 1;
        //将旧数据转移到新list中
        for (int i  = start; i < timers.size(); i++){
            newTimers.add(timers.get(i));
        }

        for ( int i = 0 ; i < start; i++){
            newTimers.add(0);
        }

        return newTimers;

    }



}
