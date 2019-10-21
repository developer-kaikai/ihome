package com.shixun.ihome.work.service.serviceImpl;

import com.shixun.ihome.publicservice.mapper.ITimerMapper;
import com.shixun.ihome.publicservice.pojo.ITimer;
import com.shixun.ihome.publicservice.pojo.LabelValue;
import com.shixun.ihome.publicservice.pojo.RedisTimer;
import com.shixun.ihome.publicservice.pojo.RedisTimerInfo;
import com.shixun.ihome.work.service.RedisTimerService;
import com.shixun.ihome.work.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RedisTimerServiceImpl implements RedisTimerService {
    @Autowired
    private ITimerMapper timerMapper;

    @Override
    public List<RedisTimerInfo> getMessageOther(Integer detailTypeId) {
        return null;
    }

    @Override
    public List<RedisTimerInfo> getMessage(Integer detailTypeId, Integer hours) {
        //获取时间表
        List<ITimer> times =  timerMapper.selectStaffTimer(detailTypeId);
        List<Integer> timers = timeSpilt(timerSum(times, 0), 0);
        System.out.println(timers);
        List<RedisTimerInfo> timerInfos = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        for ( int i = 1; i <= 7; i++){
            //获取时间
            int t = timers.get(i);
            //判读时间表是否为满 63:二进制111111
            if (t != 63){
                RedisTimerInfo redisTimerInfo = new RedisTimerInfo();
                String weekDay = toWeekDay(calendar);
                int pos = weekDay.indexOf('(');
                String value = weekDay.substring(0, pos);
                redisTimerInfo.setDate(weekDay);
                redisTimerInfo.setValue(value);
                List<LabelValue> time = null;
                time = toTimers(t, hours, value);
                redisTimerInfo.setTimer(time);
                timerInfos.add(redisTimerInfo);
            }
            calendar.add(Calendar.DAY_OF_MONTH  ,1);
        }
        return timerInfos;
    }

    private List<LabelValue> toTimers2(int timer, int hours, String date){
        List<LabelValue> timers = new ArrayList<>();
        int hour = 8;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        int maxhour = 18;
        return timers;
    }

    //时间想与
    private Long timerSum(List<ITimer> timers, Integer type){
        //如果获取的type位0就是钟点工
        Long timer = 0l;
        if(type == 0){
            timer = ITimerMapper.MAXTIMER;
        }else{
            timer = 255l;
        }
        if (timer == 0){
            throw new RuntimeException("RedisTimerService 的 timerSum 存在参数问题");
        }
        //开始相与
        for (ITimer timer1 : timers) {
            timer = timer & timer1.getTimer();
        }

        return timer;
    }
    //将时间分割
    private List<Integer> timeSpilt(Long timer, Integer type){
        System.out.println(timer);
        List<Integer> timers = new ArrayList<>();
        int index = 1;
        int pos = 1;
        if (type == 0){
            //钟点工
            index = 6;
            pos = 63;
        }
        for (int i = 1; i <= 8; i++){
            System.out.println(Long.toBinaryString(timer));
            timers.add( (int)(timer & pos) );//获取6位数字
            timer = timer >>index;
        }
        return timers;
    }


    private List<LabelValue> toTimers(int timer, int hours, String date){
        List<LabelValue> timers = new ArrayList<>();
        int hour = 8;
        int maxhour = 20;
        for (int i =0; i <= 5;i++){
            int t = (timer >> i )& 1;
            //根据0或1生成时间表
            if (hour + hours >= maxhour){
                String s = String.format("%d:00 - %d:00", hour, hour + hours);
                LabelValue labelValue = new LabelValue( s, String.format("%s %d:00",date, hour)+ "|" + String.format("%s %d:00",date, hour + hours));
                timers.add(labelValue);
                break;
            }
            if (t == 0){
                //代表是这个时间端还可以被选择
                for (int j = 0; j < 2; j++){
                    String s = String.format("%d:00 - %d:00", hour , hour + hours );
                    LabelValue labelValue1 = new LabelValue( s, String.format("%s %d:00",date, hour) + "|" + String.format("%s %d:00",date, hour + hours));
                    timers.add(labelValue1);
                    if(hour + hours == maxhour){
                        break;
                    }
                    String s1 = String.format("%d:30 - %d:30", hour , hour+ hours );
                    LabelValue labelValue2 = new LabelValue( s1,String.format("%s %d:30:00",date, hour) + "|" + String.format("%s %d:30:00",date, hour + hours));
                    timers.add(labelValue2);
                    hour += 1;
                }
            }else{
                hour += 2;
            }

        }
        return timers;
    }


    private String toWeekDay(Calendar calendar){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String s = null;
        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case 7: s = "周一";break;
            case 1: s = "周二";break;
            case 2: s = "周三";break;
            case 3: s = "周四";break;
            case 4: s = "周五";break;
            case 5: s = "周六";break;
            case 6: s = "周日";break;
        }
        return String.format("%d年%d月%d日(%s)", year, month, day, s);
    }
}
