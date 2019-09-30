package com.shixun.ihome.maintenance.service;

import com.shixun.ihome.publicservice.pojo.IStaff;

import java.util.List;
import java.util.Map;

public interface StaffService {
    /**
     * 通过状态（state）搜索员工
     * @param state
     * 0：空闲中  1：休假中   2：服务中
     * @return
     */
    List<IStaff> selectStaffByState(int state);

    /**
     * 根据服务类型搜索员工
     * @param servicetype_id 服务类型id
     * @return
     */
    List<IStaff> selectStaffByServicetypeId(int servicetype_id);

    /**
     * 通过map查询员工
     * @param istaff
     * @return
     */
    List<IStaff> selectStaffs(Map<String,Object> istaff);

    /**
     * 删除员工，并非真正删除
     * @param id    员工id
     * @return
     */
    int deleteStaff(int id, String byWho);

    /**
     * 修改员工信息
     * @param iStaff 修改的员工信息
     * @param byWho 修改人是谁
     * @return
     */
    int updateStaff(IStaff iStaff, String byWho);

    /**
     * 新添员工信息
     * @param iStaff    员工信息
     * @param byWho 插入
     * @return
     */
    int insertStaff(IStaff iStaff, String byWho);
}
