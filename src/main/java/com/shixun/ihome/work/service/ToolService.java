package com.shixun.ihome.work.service;

import com.shixun.ihome.publicservice.pojo.IOrder;
import com.shixun.ihome.publicservice.pojo.ITool;
import com.shixun.ihome.publicservice.pojo.IToolrecord;

import java.util.List;

public interface ToolService {

    /*员工查看自己的工具*/
    List<IToolrecord> listbystaffid(int staffid,int state);



    /*员工领取工具*/
    Boolean receiveTool(int orderid,int staffid);

    /*员工完成后释放工具*/
    Boolean returnTool(int orderid,int staffid);



    /*工具损坏*/
    Boolean damageTool(int orderid,int staffid);

    /*工具增删查改*/
    Boolean addTool(ITool iTool);

    Boolean deleteTool(int itoolid);

    Boolean updateTool(ITool iTool);

    List<ITool> selectByname(String itoolname);

    List<ITool> selectAll();

    /*工具记录*/
    List<IToolrecord> allRecord(int state);

    Boolean deteToolrecord(int recordid);

    Boolean updateToolrecord(IToolrecord iToolrecord);
    //添加工具记录
    Boolean addToolrecord(IToolrecord iToolrecord);
    //获取工具
    ITool getOne(int detailId);

    List<IToolrecord> selectByOrderId(int orderId);


}
