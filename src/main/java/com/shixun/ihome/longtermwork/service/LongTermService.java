package com.shixun.ihome.longtermwork.service;

import com.shixun.ihome.publicservice.pojo.IOrder;
import com.shixun.ihome.publicservice.pojo.IOrderLong;
import com.shixun.ihome.publicservice.pojo.IStaff;

import java.util.List;

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

    /*
    * 添加订单详情
    * */
    boolean addOrderDetails(IOrderLong orderLong);
    /*
    * 查询空闲状态的长期工
    * */
    List<IStaff> selectLongTermStaffs();
}
