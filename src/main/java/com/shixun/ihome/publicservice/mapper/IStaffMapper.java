package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IStaff;
import com.shixun.ihome.publicservice.pojo.IStaffExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IStaffMapper {
    int countByExample(IStaffExample example);

    int deleteByExample(IStaffExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IStaff record);

    int insertSelective(IStaff record);

    List<IStaff> selectByExample(IStaffExample example);

    IStaff selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IStaff record, @Param("example") IStaffExample example);

    int updateByExample(@Param("record") IStaff record, @Param("example") IStaffExample example);

    int updateByPrimaryKeySelective(IStaff record);

    int updateByPrimaryKey(IStaff record);
}