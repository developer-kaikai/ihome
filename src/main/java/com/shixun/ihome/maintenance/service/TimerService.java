package com.shixun.ihome.maintenance.service;

import com.shixun.ihome.publicservice.pojo.ITimer;
import java.util.List;

public interface TimerService {
    /**
     * 根据时间表查询空闲员工
     * @param timer  二进制 14个零一 代表 之后7天上下午的空闲与工作 1是工作 0 是空闲
     * @return
     */
    List<ITimer> selectFreeStaff(int timer);
    List<Integer> selectFreeStaffForStaffId(int timer);
}
