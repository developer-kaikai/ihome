package com.shixun.ihome.maintenance.service;

import com.shixun.ihome.publicservice.pojo.ITimer;
import java.util.List;

public interface TimerService {
    List<ITimer> selectFreeStaff(int timer);
    List<Integer> selectFreeStaffForStaffId(int timer);
}
