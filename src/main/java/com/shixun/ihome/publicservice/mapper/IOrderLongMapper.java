package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IOrderLong;
import com.shixun.ihome.publicservice.pojo.IOrderLongExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IOrderLongMapper {
    int countByExample(IOrderLongExample example);

    int deleteByExample(IOrderLongExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IOrderLong record);

    int insertSelective(IOrderLong record);

    List<IOrderLong> selectByExample(IOrderLongExample example);

    IOrderLong selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IOrderLong record, @Param("example") IOrderLongExample example);

    int updateByExample(@Param("record") IOrderLong record, @Param("example") IOrderLongExample example);

    int updateByPrimaryKeySelective(IOrderLong record);

    int updateByPrimaryKey(IOrderLong record);
}