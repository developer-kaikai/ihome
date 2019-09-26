package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IOrderStaff;
import com.shixun.ihome.publicservice.pojo.IOrderStaffExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IOrderStaffMapper {
    int countByExample(IOrderStaffExample example);

    int deleteByExample(IOrderStaffExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IOrderStaff record);

    int insertSelective(IOrderStaff record);

    List<IOrderStaff> selectByExample(IOrderStaffExample example);

    IOrderStaff selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IOrderStaff record, @Param("example") IOrderStaffExample example);

    int updateByExample(@Param("record") IOrderStaff record, @Param("example") IOrderStaffExample example);

    int updateByPrimaryKeySelective(IOrderStaff record);

    int updateByPrimaryKey(IOrderStaff record);
}