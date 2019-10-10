package com.shixun.ihome.work.service.serviceImpl;

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
    public List<IStaff> selectStaffByServiceTypeAndStatus(int type, int status) {
        Map<String, Object> map  = new HashMap<String, Object>();
        map.put("type", type);
        map.put("status", status);
        return staffMapper.selectStaffByServiceTypeAndStatus(map);
    }



    @Override
    public List<IStaff> selectStaffs(IStaff istaff) {
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


        }

        return staffMapper.selectByExample(iStaffExample);
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
        return staffMapper.insert(record) > 0;
    }

    @Override
    public IStaff getOne(int id) {
        return staffMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<IStaff> selectHourworkStaffsByStatus(Map<String, Object> map) {
        return staffMapper.selectHourworkStaffsByStatus(map);
    }
}
