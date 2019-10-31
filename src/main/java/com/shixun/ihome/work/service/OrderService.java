package com.shixun.ihome.work.service;

import com.github.pagehelper.PageInfo;
import com.shixun.ihome.publicservice.pojo.IOrder;
import com.shixun.ihome.publicservice.pojo.IOrderLong;
import com.shixun.ihome.publicservice.pojo.IOrderStaff;
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
    boolean addOrderRecord(IOrder order, String bywho);

    boolean updateOrderState(int orderId, int state);
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
     * @param orderId     订单
     * @param staffId    员工
     * @return  是否全部添加成功
     */
    boolean addStaffForOrder(int orderId, int staffId);

    /**
     * 移除订单中分配的一个人员
     * @param orderId  订单id
     * @param staffId   员工id
     * @return
     */
    boolean removeStaffForOrder(int orderId, int staffId);

    /*订单高级筛选*/
    List<IOrder> listByCondition(IOrder order);

    PageInfo<IOrder> listByConditionPage(IOrder order, int pageNum,int pageSize);


    List<IOrder> listbyuserid(int userid,int orderstate);
    List<IOrder> listbystaffid(int staffid,int orderstate);
    List<IOrder> listbystaffidtwo(int staffid,int orderstate);

    List<IOrder> listbyuserTypename(int userid,String typename);
    List<IOrder> listbystaffTypename(int staffid,String typename);

    /*双向确认订单*/
    Boolean updateOrderState(int orderid);


    PageInfo<IOrder> selectByCondition(IOrder order, int pageNum, int pageSize);

    List<IOrderStaff> selectOrderStaffs(int orderId);

    boolean addOrderLongDetail(IOrderLong orderLong,IOrderStaff orderStaff);


}
