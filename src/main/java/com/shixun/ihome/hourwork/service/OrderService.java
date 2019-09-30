package com.shixun.ihome.hourwork.service;

import com.shixun.ihome.publicservice.pojo.IOrder;

public interface OrderService {
    //下单
    boolean addOrder(IOrder order);
    //取消订单
    boolean cancelOrder(int id);
    //修改订单
    boolean updateOrder(IOrder order);
    //删除订单
    boolean deleteOrder(int id);
}
