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
    public PageInfo<IStaff> selectStaffs(IStaff istaff, int pageNum, int pageSize) {
        IStaffExample iStaffExample = new IStaffExample();
        IStaffExample.Criteria criteria = iStaffExample.createCriteria();
        if(istaff != null){
            if (istaff.getStatus() != null) {
                criteria.andStatusEqualTo(istaff.getStatus());
            }
            if(istaff.getPhone() != null){
                criteria.andPhoneLike(istaff.getPhone() + "%");
            }
            if (istaff.getSex() != null){
                criteria.andSexEqualTo(istaff.getSex());
            }
            if(istaff.getDetailtypeId() != null){
                criteria.andDetailtypeIdEqualTo(istaff.getDetailtypeId());
            }
            if (istaff.getName() != null){
                criteria.andNameLike(istaff.getName() + "%");
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<IStaff> staffs = new PageInfo<>(staffMapper.selectByExample(iStaffExample));
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
    public boolean updateStaffFile(IStaff staff) {
        int result = staffMapper.updateByPrimaryKeySelective(staff);
        if(result > 0){
            return true;
        }
        return false;
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
    public boolean updateStaffStatus(int staffId, int staffStatus, int wantStatus) {
        IStaffExample iStaffExample = new IStaffExample();
        IStaffExample.Criteria criteria = iStaffExample.createCriteria();
        criteria.andStatusEqualTo(wantStatus);
        criteria.andIdEqualTo(staffId);
        IStaff iStaff = new IStaff();
        iStaff.setStatus(staffStatus);
        if(staffMapper.updateByExampleSelective(iStaff, iStaffExample) > 0){
            return true;
        }
        else{
            return false;
        }
    }





    @Override
    public List<IStaff> selectStaffForOrder(Integer orderId) {
        return staffMapper.selectStaffForOrder(orderId);
    }
}
