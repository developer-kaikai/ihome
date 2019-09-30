package com.shixun.ihome.longtermwork.service;

import com.shixun.ihome.publicservice.pojo.IOrder;
import com.shixun.ihome.publicservice.pojo.IOrderLong;

public interface LongTermService {
    /*
    * 添加订单
    * */
    boolean addOrder(IOrder order);

    /*
    * 取消订单
    * */
    boolean cancelOrder(int id);
    /*
    * 确认订单
    * */
    boolean confirmOrder(int id);
    /*
    * 分配员工，上传员工信息
    * */
    boolean addOrderLong(IOrderLong orderLong);
}
