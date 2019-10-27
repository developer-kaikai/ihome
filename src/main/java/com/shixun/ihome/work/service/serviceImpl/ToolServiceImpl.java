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
    public List<IToolrecord> listbystaffid(int staffid,int state) {
        return iToolrecordMapper.selectbystaff(staffid,state);
    }

    @Override
    public Boolean receiveTool(int orderid, int staffid) {
        IToolrecord iToolrecord=iToolrecordMapper.selectToolrecord(orderid,staffid);
        iToolrecord.setState(1);
        iToolrecordMapper.updateByPrimaryKeySelective(iToolrecord);
        ITool iTool=iToolMapper.selectByPrimaryKey(iToolrecord.getToolId());
        iTool.setTcount(iTool.getTcount()-1);
        iToolMapper.updateByPrimaryKeySelective(iTool);

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
    public List<IToolrecord> allRecord(int state) {
        return iToolrecordMapper.selectall(state);
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

    @Override
    public Boolean addToolrecord(IToolrecord iToolrecord) {
        int result = iToolrecordMapper.insert(iToolrecord);
        if (result > 0){
            return true;
        }
        throw new RuntimeException("添加工具订单失败");
    }


    @Override
    public ITool getOne(int detailId) {
        IToolExample toolExample = new IToolExample();
        IToolExample.Criteria criteria = toolExample.createCriteria();
        criteria.andDetailtypeIdEqualTo(detailId);
        ITool tool  = iToolMapper.selectByExample(toolExample).get(0);
        return tool;
    }
}
