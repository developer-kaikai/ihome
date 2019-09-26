package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.ITrain;
import com.shixun.ihome.publicservice.pojo.ITrainExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ITrainMapper {
    int countByExample(ITrainExample example);

    int deleteByExample(ITrainExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ITrain record);

    int insertSelective(ITrain record);

    List<ITrain> selectByExample(ITrainExample example);

    ITrain selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ITrain record, @Param("example") ITrainExample example);

    int updateByExample(@Param("record") ITrain record, @Param("example") ITrainExample example);

    int updateByPrimaryKeySelective(ITrain record);

    int updateByPrimaryKey(ITrain record);
}