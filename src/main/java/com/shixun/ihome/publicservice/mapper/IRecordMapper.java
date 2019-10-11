package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IRecord;
import com.shixun.ihome.publicservice.pojo.IRecordExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRecordMapper {
    //修改
    int UPDATED = 1;
    //删除
    int DELETED = 2;
    //插入
    int INSERT = 0;

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