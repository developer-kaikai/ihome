package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IServiceTimer;
import com.shixun.ihome.publicservice.pojo.IServiceTimerExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IServiceTimerMapper {
    int countByExample(IServiceTimerExample example);

    int deleteByExample(IServiceTimerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IServiceTimer record);

    int insertSelective(IServiceTimer record);

    List<IServiceTimer> selectByExample(IServiceTimerExample example);

    IServiceTimer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IServiceTimer record, @Param("example") IServiceTimerExample example);

    int updateByExample(@Param("record") IServiceTimer record, @Param("example") IServiceTimerExample example);

    int updateByPrimaryKeySelective(IServiceTimer record);

    int updateByPrimaryKey(IServiceTimer record);
}