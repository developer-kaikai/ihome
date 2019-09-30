package com.shixun.ihome.maintenance.service.serviceImpl;

import com.shixun.ihome.maintenance.service.TimerService;
import com.shixun.ihome.publicservice.mapper.ITimerMapper;
import com.shixun.ihome.publicservice.pojo.ITimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Integer> selectFreeStaffForStaffId(int timer) {
        return iTimerMapper.selectFreeStaffForStaffId(timer);
    }
}
