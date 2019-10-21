package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IDetailTimer;
import com.shixun.ihome.publicservice.pojo.IDetailTimerExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDetailTimerMapper {
    int countByExample(IDetailTimerExample example);

    int deleteByExample(IDetailTimerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IDetailTimer record);

    int insertSelective(IDetailTimer record);

    List<IDetailTimer> selectByExample(IDetailTimerExample example);

    IDetailTimer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IDetailTimer record, @Param("example") IDetailTimerExample example);

    int updateByExample(@Param("record") IDetailTimer record, @Param("example") IDetailTimerExample example);

    int updateByPrimaryKeySelective(IDetailTimer record);

    int updateByPrimaryKey(IDetailTimer record);
}