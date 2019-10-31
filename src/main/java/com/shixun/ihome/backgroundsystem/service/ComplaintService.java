package com.shixun.ihome.backgroundsystem.service;

import com.github.pagehelper.PageInfo;
import com.shixun.ihome.publicservice.pojo.IOrder;
import com.shixun.ihome.publicservice.pojo.IOrderComplaint;

import java.util.List;

public interface ComplaintService {
    /*用户订单投诉*/
    Boolean addComplaint(IOrderComplaint complaint);

    /*投诉处理*/
    Boolean solveCompaint(IOrderComplaint complaint);

    /*查看所有投诉*/
    List<IOrderComplaint> complaintlistAll();

    /*
    * 根据投诉状态查询*/
    PageInfo<IOrderComplaint> selectComplaintByStatus(IOrderComplaint complaint, int pageNum, int pageSize);

}
