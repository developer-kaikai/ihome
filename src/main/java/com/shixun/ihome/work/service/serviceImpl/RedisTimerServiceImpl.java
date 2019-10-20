package com.shixun.ihome.work.service.serviceImpl;

import com.shixun.ihome.publicservice.pojo.LabelValue;
import com.shixun.ihome.publicservice.pojo.RedisTimer;
import com.shixun.ihome.publicservice.pojo.RedisTimerInfo;
import com.shixun.ihome.work.service.RedisTimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RedisTimerServiceImpl implements RedisTimerService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public RedisTimer getTimer() {
        //获取存储在redis中的时间表
        RedisTimer redisTimer = (RedisTimer)redisTemplate.opsForValue().get("timers");
        //检测时间表是否为空，如果为空，就重新生成新的时间表
        if (redisTimer == null){
           redisTimer = new RedisTimer();
           redisTemplate.opsForValue().set("timers", redisTimer);
            System.out.println("这个数据表是空的");
        }
        //检测数据是否最新
        if(redisTimer.update()){
            System.out.println("测试 这个数据不是最新的所以更新");
            redisTemplate.opsForValue().set("timers", redisTimer);
        }
        return redisTimer;
    }

    @Override
    public void setTime(int index, int timer) {
        System.out.println("索引:" + index + ", 时间表:" + timer);
        RedisTimer redisTimer = getTimer();
        List<Integer> timers =  redisTimer.getTimers();
        timers.set(index, timer);
        redisTimer.setTimers(timers);
        redisTemplate.opsForValue().set("timers", redisTimer);
    }

    @Override
    public List<RedisTimerInfo> getMessage(int hours, int type) {
        //获取时间表
        RedisTimer redisTimer = getTimer();
        List<Integer> timers = redisTimer.getTimers();
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
                if (type == 0){
                    time = toTimers(t, hours, value);
                }else {
                    time = toTimers2(t, hours, value);
                }
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
        int maxhour = 20;
        for (int i = 0; i < 5; i++){
            int t = (timer >> i) & 1;
            if (hour + hours >= maxhour){
                String s = String.format("%d:00", hour);
                LabelValue labelValue = new LabelValue( s, String.format("%s %d:00",date, hour));
                timers.add(labelValue);
                break;
            }
            if (t == 0){
                for(int j = 0; j < 2; j++){
                    String s = String.format("%d:00", hour);
                    LabelValue labelValue1 = new LabelValue(s, String.format("%s %d:00",date, hour));
                    timers.add(labelValue1);
                    if(hour + hours == maxhour){
                        break;
                    }
                    String s1 = String.format("%d:30", hour );
                    LabelValue labelValue2 = new LabelValue( s1,String.format("%s %d:30:00",date, hour));
                    timers.add(labelValue2);
                    hour += 1;
                }
            }else {
                hours += 2;
            }
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
