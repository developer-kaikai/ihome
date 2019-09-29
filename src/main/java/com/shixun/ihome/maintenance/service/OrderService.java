package com.shixun.ihome.maintenance.service;

import com.shixun.ihome.publicservice.pojo.IOrder;

public interface OrderService {
    /*用户下单*/
    boolean addOrder(IOrder order);
    /*取消订单*/
    boolean cancelOrder(int id);
    /*维修详情*/
    boolean addDetail(int id,String o_describe,String solve);

}
