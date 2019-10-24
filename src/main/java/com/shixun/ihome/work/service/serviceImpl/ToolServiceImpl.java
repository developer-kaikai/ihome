package com.shixun.ihome.work.service.serviceImpl;


import com.shixun.ihome.publicservice.mapper.IOrderMapper;
import com.shixun.ihome.publicservice.mapper.IToolMapper;
import com.shixun.ihome.publicservice.mapper.IToolrecordMapper;
import com.shixun.ihome.publicservice.pojo.*;
import com.shixun.ihome.work.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolServiceImpl implements ToolService {
    @Autowired
    private IToolMapper iToolMapper;
    @Autowired
    private IToolrecordMapper iToolrecordMapper;
    @Autowired
    private IOrderMapper orderMapper;


    @Override
    public Boolean receiveTool(int orderid, int staffid) {
        IOrder order=orderMapper.selectByPrimaryKey(orderid);
        IToolrecord iToolrecord=new IToolrecord();
        iToolrecord.setOrderId(orderid);
        iToolrecord.setStaffId(staffid);

        IToolExample example=new IToolExample();
        IToolExample.Criteria criteria=example.createCriteria();
        criteria.andDetailtypeIdEqualTo(order.getDetailtypeId());

        List<ITool> iToolList=iToolMapper.selectByExample(example);
        ITool iTool=new ITool();
        iTool=iToolList.get(0);
        iTool.setCount(iTool.getCount()-1);
        iToolMapper.updateByPrimaryKeySelective(iTool);
        iToolrecord.setToolId(iTool.getId());
        iToolrecord.setCount(1);
        iToolrecord.setState(1);
        iToolrecordMapper.insert(iToolrecord);
        order.setState(3);
        orderMapper.updateByPrimaryKeySelective(order);

        return true;

    }

    @Override
    public Boolean returnTool(int orderid,int staffid) {
        IToolrecord iToolrecord=iToolrecordMapper.selectToolrecord(orderid,staffid);
        iToolrecord.setState(2);
        ITool iTool=iToolMapper.selectByPrimaryKey(iToolrecord.getToolId());
        iTool.setCount(iTool.getCount()+1);
        iToolrecordMapper.updateByPrimaryKeySelective(iToolrecord);
        iToolMapper.updateByPrimaryKeySelective(iTool);

        return true;
    }

    @Override
    public Boolean damageTool(int orderid, int staffid) {
        IToolrecord iToolrecord=iToolrecordMapper.selectToolrecord(orderid,staffid);
        iToolrecord.setState(3);
        iToolrecordMapper.updateByPrimaryKeySelective(iToolrecord);


        return true;
    }
}
