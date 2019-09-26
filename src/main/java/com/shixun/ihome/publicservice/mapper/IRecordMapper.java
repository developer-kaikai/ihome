package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IRecord;
import com.shixun.ihome.publicservice.pojo.IRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IRecordMapper {
    int countByExample(IRecordExample example);

    int deleteByExample(IRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IRecord record);

    int insertSelective(IRecord record);

    List<IRecord> selectByExample(IRecordExample example);

    IRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IRecord record, @Param("example") IRecordExample example);

    int updateByExample(@Param("record") IRecord record, @Param("example") IRecordExample example);

    int updateByPrimaryKeySelective(IRecord record);

    int updateByPrimaryKey(IRecord record);
}