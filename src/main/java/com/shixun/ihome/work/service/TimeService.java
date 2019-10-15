package com.shixun.ihome.work.service;

import com.shixun.ihome.publicservice.pojo.ITimer;

import java.util.Date;
import java.util.List;

public interface TimeService {
    //插入时间表
    boolean addTimer(int staffId);

    //根据订单的开始日期和结束日期获取空闲员工
    List<ITimer> selectFreeStaff(Date startTimer, Date endTimer);

    //更新员工时间
    boolean updateTimer(int id, long timer);

    //移除员工更新时间
    boolean updateTimerRemove(int id, long timer);

    String test(Date date1, Date date2);
}
