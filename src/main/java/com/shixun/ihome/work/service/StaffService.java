package com.shixun.ihome.work.service;

import com.shixun.ihome.publicservice.pojo.IOrderLong;
import com.shixun.ihome.publicservice.pojo.IStaff;

import java.util.List;
import java.util.Map;

public interface StaffService {
    /**
     * 根据服务类型以及状态搜索员工
     * @param type 1：钟点工，2：一般家政，3：家维修，4：长期工
     * @param status  0：空闲中，1：休假中，2：服务中，3：无效
     * @return 搜索的员工列表
     */
    List<IStaff> selectStaffByServiceTypeAndStatus(int type, int status);

    //根据条件搜索员工
    List<IStaff> selectStaffs(IStaff istaff);

    //删除员工（更新员工状态）
    String deleteStaffRecord(IStaff record, String byWho);

    //修改员工信息
    boolean updateStaffRecord(IStaff newrecord,IStaff oldrecord, String byWho);

    //新增员工信息
    boolean addStaffRecord(IStaff record, String byWho);
    //获取单个员工信息
    IStaff getOne(int id);

    //获取钟点工
    List<IStaff> selectHourworkStaffsByStatus(Map<String, Object> map);
}

