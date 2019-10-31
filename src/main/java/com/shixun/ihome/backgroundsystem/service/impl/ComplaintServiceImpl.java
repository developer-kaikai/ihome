package com.shixun.ihome.backgroundsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shixun.ihome.backgroundsystem.service.ComplaintService;
import com.shixun.ihome.publicservice.mapper.IOrderComplaintMapper;
import com.shixun.ihome.publicservice.pojo.IOrder;
import com.shixun.ihome.publicservice.pojo.IOrderComplaint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Autowired
    private IOrderComplaintMapper iOrderComplaintMapper;

    @Override
    public Boolean addComplaint(IOrderComplaint complaint) {
        iOrderComplaintMapper.insert(complaint);
        return true;
    }

    @Override
    public Boolean solveCompaint(IOrderComplaint complaint) {
        iOrderComplaintMapper.updateByPrimaryKeySelective(complaint);
        return  true;
    }

    @Override
    public List<IOrderComplaint> complaintlistAll() {
        return  iOrderComplaintMapper.selectByExample(null);
    }

    @Override
    public PageInfo<IOrderComplaint> selectComplaintByStatus(IOrderComplaint complaint,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<IOrderComplaint> pages = new PageInfo<>(iOrderComplaintMapper.selectComplaintByStatus(complaint));
        return pages;
    }
}
