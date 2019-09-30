package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IOrderComplaint;
import com.shixun.ihome.publicservice.pojo.IOrderComplaintExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IOrderComplaintMapper {
    int countByExample(IOrderComplaintExample example);

    int deleteByExample(IOrderComplaintExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IOrderComplaint record);

    int insertSelective(IOrderComplaint record);

    List<IOrderComplaint> selectByExample(IOrderComplaintExample example);

    IOrderComplaint selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IOrderComplaint record, @Param("example") IOrderComplaintExample example);

    int updateByExample(@Param("record") IOrderComplaint record, @Param("example") IOrderComplaintExample example);

    int updateByPrimaryKeySelective(IOrderComplaint record);

    int updateByPrimaryKey(IOrderComplaint record);
}