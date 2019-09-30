package com.shixun.ihome.maintenance.service.serviceImpl;

import com.shixun.ihome.publicservice.mapper.IRecordMapper;
import com.shixun.ihome.publicservice.mapper.IStaffMapper;
import com.shixun.ihome.publicservice.pojo.IRecord;
import com.shixun.ihome.publicservice.pojo.IStaff;
import com.shixun.ihome.publicservice.pojo.IStaffExample;
import com.shixun.ihome.publicservice.util.Qutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StaffServiceImpl implements com.shixun.ihome.maintenance.service.StaffService {
    @Autowired
    private IStaffMapper iStaffMapper;
    @Autowired
    private IRecordMapper iRecordMapper;

    @Override
    public List<IStaff> selectStaffByState(int state) {
        IStaffExample iStaffExample = new IStaffExample();
        IStaffExample.Criteria criteria = iStaffExample.createCriteria();
        criteria.andStatusEqualTo(state);
        List<IStaff> iStaffs = iStaffMapper.selectByExample(iStaffExample);
        return iStaffs;
    }

    @Override
    public List<IStaff> selectStaffByServicetypeId(int servicetype_id) {
        return iStaffMapper.selectStaffByServicetypeId(servicetype_id);
    }

    @Override
    public List<IStaff> selectStaffs(Map<String,Object> istaff) {
        return iStaffMapper.selectStaffs(istaff);
    }

    @Override
    public int insertStaff(IStaff iStaff, String byWho) {
        return 0;
    }

    @Override
    public int deleteStaff(int id,String byWho) {
        IStaff iStaff = new IStaff();
        iStaff.setId(id);
        iStaff.setStatus(3);

        //获取旧已记录
        IStaff oldrecord = iStaffMapper.selectByPrimaryKey(id);
        IRecord iRecord = Qutil.createRecord(1, byWho, "i_staff", oldrecord.toString(), "以删除");
       //插入记录
        iRecordMapper.insert(iRecord);
        return iStaffMapper.updateByPrimaryKeySelective(iStaff);
    }

    @Override
    public int updateStaff(IStaff iStaff, String byWho) {

        IStaff oldrecord = iStaffMapper.selectByPrimaryKey(iStaff.getId());
        IRecord iRecord = Qutil.createRecord(2, byWho, "i_staff", oldrecord.toString(), iStaff.toString());
        iRecordMapper.insert(iRecord);
        return iStaffMapper.updateByPrimaryKeySelective(iStaff);
    }


}
