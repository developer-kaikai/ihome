package com.shixun.ihome.work.service;

import com.shixun.ihome.publicservice.pojo.ITimer;

import java.util.List;

public interface TimeService {
    //根据时间表搜索空闲的员工

    boolean addTimer(int staffId);

    List<ITimer> selectFreeStaff(int timer);

    //更新员工时间
    boolean updateTimer(int id, int timer);

    //移除员工更新时间
    boolean updateTimerRemove(int id, int timer);
}
