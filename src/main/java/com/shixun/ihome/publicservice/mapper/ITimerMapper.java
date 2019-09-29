package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.ITimer;
import com.shixun.ihome.publicservice.pojo.ITimerExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ITimerMapper {
    int countByExample(ITimerExample example);

    int deleteByExample(ITimerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ITimer record);

    int insertSelective(ITimer record);

    List<ITimer> selectByExample(ITimerExample example);

    ITimer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ITimer record, @Param("example") ITimerExample example);

    int updateByExample(@Param("record") ITimer record, @Param("example") ITimerExample example);

    int updateByPrimaryKeySelective(ITimer record);

    int updateByPrimaryKey(ITimer record);
}