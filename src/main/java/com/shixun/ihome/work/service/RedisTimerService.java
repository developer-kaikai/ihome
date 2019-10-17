package com.shixun.ihome.work.service;

import com.shixun.ihome.publicservice.pojo.RedisTimer;
import com.shixun.ihome.publicservice.pojo.RedisTimerInfo;

import java.util.List;

//用于redis存储人员安排的信息
public interface RedisTimerService {
    //获取Redis存储的员工安排表,如果为空就重新生成
    RedisTimer getTimer();
    //为某天设置时间表
    void setTime(int index, int timer);
    //动态生成前端需要的时间表
    List<RedisTimerInfo> getMessage(int hours);
}
