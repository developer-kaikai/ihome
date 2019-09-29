package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IOrderNews;
import com.shixun.ihome.publicservice.pojo.IOrderNewsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IOrderNewsMapper {
    int countByExample(IOrderNewsExample example);

    int deleteByExample(IOrderNewsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IOrderNews record);

    int insertSelective(IOrderNews record);

    List<IOrderNews> selectByExample(IOrderNewsExample example);

    IOrderNews selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IOrderNews record, @Param("example") IOrderNewsExample example);

    int updateByExample(@Param("record") IOrderNews record, @Param("example") IOrderNewsExample example);

    int updateByPrimaryKeySelective(IOrderNews record);

    int updateByPrimaryKey(IOrderNews record);

    IOrderNews selectByOrderID(int id);
}