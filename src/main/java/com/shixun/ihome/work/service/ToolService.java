package com.shixun.ihome.work.service;

import com.shixun.ihome.publicservice.pojo.ITool;
import com.shixun.ihome.publicservice.pojo.IToolrecord;

import java.util.List;

public interface ToolService {
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
    List<IToolrecord> allRecord();

    Boolean deteToolrecord(int recordid);

    Boolean updateToolrecord(IToolrecord iToolrecord);


}
