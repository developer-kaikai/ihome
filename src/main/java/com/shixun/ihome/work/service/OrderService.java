package com.shixun.ihome.work.service;

import com.shixun.ihome.publicservice.pojo.IOrder;
import com.shixun.ihome.publicservice.pojo.IOrderLong;
import com.shixun.ihome.publicservice.pojo.IStaff;

import java.util.List;

public interface OrderService {
    //订单类型
    //钟点工
    int PARTTIMEJOB = 1;
    //一般家政
    int HOUSEHOLD = 2;
    //家电维修
    int REPAIR = 3;
    //长期工
    int PERMANENTWORKER = 4;

    /*用户下单*/
    IOrder addOrderRecord(IOrder order, String bywho);
    /*取消订单*/
    boolean cancelOrder(int id);
    /*维修详情*/
    boolean addDetail(int id, String o_describe, String solve, Double price);
    /*订单评价*/
    boolean addEvaluate(int id, int quality_valuation, int attitude_valuation, String describe);
    //删除订单
    boolean deleteOrder(int id);
    //根据条件查询订单
    List<IOrder> selectOrder(IOrder order);
    //获取订单
    IOrder getOrder(int id);
    /*查看所有订单*/
    List<IOrder> listAll();
    //添加长期工订单信息addOrderLong
    boolean addOrderLong(IOrderLong orderLong);
    /**
     *  为订单分配员工
     * @param order     订单
     * @param staffs    员工
     * @return  是否全部添加成功
     */
    boolean addStaffForOrder(IOrder order, IStaff staffs);

    /**
     * 移除某订单的某个员工
     * @param order 订单
     * @param staff 员工
     * @param orderType 订单类型
     * @return 是否移除成功
     */
    boolean removeStaffForOrder(IOrder order, IStaff staff, int orderType);

    /*订单高级筛选*/
    List<IOrder> listByCondition(IOrder order);

}
