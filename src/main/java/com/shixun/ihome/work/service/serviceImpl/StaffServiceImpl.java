package com.shixun.ihome.work.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shixun.ihome.publicservice.mapper.IStaffMapper;
import com.shixun.ihome.publicservice.pojo.IOrderLong;
import com.shixun.ihome.publicservice.pojo.IStaff;
import com.shixun.ihome.publicservice.pojo.IStaffExample;
import com.shixun.ihome.work.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private IStaffMapper staffMapper;


    @Override
    public PageInfo<IStaff> selectStaffByServiceTypeAndStatus(int type, int status, int pageNum, int pageSize) {
        Map<String, Object> map  = new HashMap<String, Object>();
        map.put("type", type);
        map.put("status", status);
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<IStaff> staffs = new PageInfo<IStaff>(staffMapper.selectStaffByServiceTypeAndStatus(map));
        return staffs;
    }

    @Override
    public PageInfo<IStaff> selectStaffs(IStaff istaff, int pageNum, int pageSize) {
        IStaffExample iStaffExample = new IStaffExample();
        IStaffExample.Criteria criteria = iStaffExample.createCriteria();
        if(istaff != null){
            if (istaff.getStatus() != null) {
                criteria.andStatusEqualTo(istaff.getStatus());
            }
            if (istaff.getSex() != null){
                criteria.andSexEqualTo(istaff.getSex());
            }
            if(istaff.getDetailtypeId() != null){
                criteria.andDetailtypeIdEqualTo(istaff.getDetailtypeId());
            }
            if (istaff.getName() != null){
                criteria.andNameLike(istaff.getName());
            }
        }
        PageInfo<IStaff> staffs = new PageInfo<IStaff>(staffMapper.selectByExample(iStaffExample));
        return staffs;
    }


    @Override
    public String deleteStaffRecord(IStaff record, String byWho) {
        if (record.getStatus() == IStaffMapper.IVALID){
            return "员工记录已删除";
        }
        record.setStatus(IStaffMapper.IVALID);
        if(staffMapper.updateByPrimaryKeySelective(record) > 0){
            return "员工删除成功";
        }

        return "员工删除失败";
    }

    @Override
    public boolean updateStaffRecord(IStaff newrecord, IStaff oldrecord, String byWho) {
        return staffMapper.updateByPrimaryKeySelective(newrecord) > 0;
    }

    @Override
    public boolean addStaffRecord(IStaff record, String byWho) {
        return staffMapper.insertSelective(record) > 0;
    }

    @Override
    public IStaff getOne(int id) {
        return staffMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<IStaff> selectHourworkStaffsByStatus(Map<String, Object> map) {
        int pageNum = (Integer)(map.get("pageNum"));
        int pageSize = (Integer)(map.get("pageSize"));
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<IStaff> staffs = new PageInfo<IStaff>(staffMapper.selectHourworkStaffsByStatus(map));
        return staffs;
    }

    @Override
    public boolean updateStaffStatus(int staffId, int staffStatus) {
        IStaff iStaff = new IStaff();
        iStaff.setId(staffId);
        iStaff.setStatus(staffStatus);
        if(staffMapper.updateByPrimaryKeySelective(iStaff) == 0){
            throw new RuntimeException("更新员工状态出现问题");
        }

        return true;
    }
}
