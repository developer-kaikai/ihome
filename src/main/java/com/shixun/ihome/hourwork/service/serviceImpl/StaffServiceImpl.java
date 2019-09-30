package com.shixun.ihome.hourwork.service.serviceImpl;

import com.shixun.ihome.hourwork.service.StaffService;
import com.shixun.ihome.publicservice.mapper.IRecordMapper;
import com.shixun.ihome.publicservice.mapper.IStaffMapper;
import com.shixun.ihome.publicservice.pojo.IRecord;
import com.shixun.ihome.publicservice.pojo.IStaff;
import com.shixun.ihome.publicservice.pojo.IStaffExample;
import com.shixun.ihome.publicservice.util.Qutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
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
    public List<IStaff> selectStaffs(IStaff istaff) {
        IStaffExample iStaffExample = new IStaffExample();
        IStaffExample.Criteria criteria = iStaffExample.createCriteria();
        if (istaff.getId()!= null) {
            criteria.andIdEqualTo(istaff.getId());
        }
        if(istaff.getDetailtypeId() != null) {
            criteria.andIdEqualTo(istaff.getDetailtypeId());
        }
        if (istaff.getHealth() != null){
            criteria.andHealthEqualTo(istaff.getHealth());
        }
        if(istaff.getIdCard() != null) {
            criteria.andIdCardEqualTo(istaff.getIdCard());
        }
        if(istaff.getName() != null) {
            criteria.andNameEqualTo(istaff.getName());
        }
        if (istaff.getPhone() != null ){
            criteria.andPhoneEqualTo(istaff.getPhone());
        }
        if(istaff.getQualification() != null){
            criteria.andQualificationEqualTo(istaff.getQualification());
        }
        if(istaff.getSex() != null) {
            criteria.andSexEqualTo(istaff.getSex());
        }
        if(istaff.getStatus() != null) {
            criteria.andStatusEqualTo(istaff.getStatus());
        }
        if(istaff.getWechatId() != null) {
            criteria.andWechatIdEqualTo(istaff.getWechatId());
        }
        iStaffExample.setOrderByClause("id desc");
        return iStaffMapper.selectByExample(iStaffExample);
    }

    @Override
    public int insertStaff(IStaff iStaff, String byWho) {
        IRecord iRecord = Qutil.createRecord(0, byWho, "i_staff", "",iStaff.toString() );
        iRecordMapper.insert(iRecord);
        return iStaffMapper.insert(iStaff);
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
