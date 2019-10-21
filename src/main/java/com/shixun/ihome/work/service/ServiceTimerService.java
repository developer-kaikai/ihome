package com.shixun.ihome.work.service;

import com.shixun.ihome.publicservice.pojo.IServiceTimer;

import java.util.Date;

public interface ServiceTimerService {
    //获取ServiceTimer并且检测更新
    IServiceTimer getOne(int serviceId);
    //转换成Long时间表
    Long toTimer(IServiceTimer timer);
    //添加订单时更新时间表
    boolean addTimer(IServiceTimer timer, Date date);
    //撤销订单时更新时间表
    boolean cancelTimer(IServiceTimer timer, Date date);
}
