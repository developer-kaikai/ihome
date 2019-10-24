package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IToolrecord;
import com.shixun.ihome.publicservice.pojo.IToolrecordExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IToolrecordMapper {
    int countByExample(IToolrecordExample example);

    int deleteByExample(IToolrecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IToolrecord record);

    int insertSelective(IToolrecord record);

    List<IToolrecord> selectByExample(IToolrecordExample example);

    IToolrecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IToolrecord record, @Param("example") IToolrecordExample example);

    int updateByExample(@Param("record") IToolrecord record, @Param("example") IToolrecordExample example);

    int updateByPrimaryKeySelective(IToolrecord record);

    int updateByPrimaryKey(IToolrecord record);

    IToolrecord selectToolrecord(int orderid,int staffid);
}