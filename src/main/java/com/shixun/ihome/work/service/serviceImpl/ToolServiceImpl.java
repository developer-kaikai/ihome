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
        iTool.setTcount(iTool.getTcount()-1);
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
        iTool.setTcount(iTool.getTcount()+1);
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

    @Override
    public Boolean addTool(ITool iTool) {
        iToolMapper.insert(iTool);
        return true;
    }

    @Override
    public Boolean deleteTool(int itoolid) {
        iToolMapper.deleteByPrimaryKey(itoolid);
        return true;
    }

    @Override
    public Boolean updateTool(ITool iTool) {
        iToolMapper.updateByPrimaryKeySelective(iTool);
        return true;
    }

    @Override
    public List<ITool> selectByname(String itoolname) {
        List<ITool> iToolList=iToolMapper.selectByname(itoolname);
        return iToolList;
    }

    @Override
    public List<ITool> selectAll() {
        return iToolMapper.selectByExample(null);
    }

    @Override
    public List<IToolrecord> allRecord() {
        return iToolrecordMapper.selectall();
    }

    @Override
    public Boolean deteToolrecord(int recordid) {
        iToolrecordMapper.deleteByPrimaryKey(recordid);
        return true;
    }

    @Override
    public Boolean updateToolrecord(IToolrecord iToolrecord) {
        iToolrecordMapper.updateByPrimaryKeySelective(iToolrecord);
        return true;
    }
}
