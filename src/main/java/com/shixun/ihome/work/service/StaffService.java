package com.shixun.ihome.work.service;

import com.github.pagehelper.PageInfo;
import com.shixun.ihome.publicservice.pojo.IOrderLong;
import com.shixun.ihome.publicservice.pojo.IStaff;

import java.util.List;
import java.util.Map;

public interface StaffService {

    //根据条件搜索员工
    PageInfo<IStaff> selectStaffs(IStaff istaff, int pageNum, int pageSize);

    //删除员工（更新员工状态）
    String deleteStaffRecord(IStaff record, String byWho);

    //修改员工信息
    boolean updateStaffRecord(IStaff newrecord,IStaff oldrecord, String byWho);

    //新增员工信息
    boolean addStaffRecord(IStaff record, String byWho);
    //获取单个员工信息
    IStaff getOne(int id);

    //获取钟点工
    PageInfo<IStaff> selectHourworkStaffsByStatus(Map<String, Object> map);

    //员工服务状态修改（不记录）
    boolean updateStaffStatus(int staffId , int staffStatus, int wantStatus);

    List<IStaff> selectStaffForOrder(Integer orderId);

}