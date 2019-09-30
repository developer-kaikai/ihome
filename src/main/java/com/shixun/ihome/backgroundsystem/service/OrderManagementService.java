package com.shixun.ihome.backgroundsystem.service;

import com.shixun.ihome.publicservice.pojo.IOrder;

import java.util.List;

public interface OrderManagementService {
    /*查看所有订单*/
    List<IOrder> listAll();
    /*删除订单*/
    boolean deleteOrder(int id);
}
