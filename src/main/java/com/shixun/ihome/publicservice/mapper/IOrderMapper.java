package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IOrder;
import com.shixun.ihome.publicservice.pojo.IOrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IOrderMapper {
    int countByExample(IOrderExample example);

    int deleteByExample(IOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IOrder record);

    int insertSelective(IOrder record);

    List<IOrder> selectByExample(IOrderExample example);

    IOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IOrder record, @Param("example") IOrderExample example);

    int updateByExample(@Param("record") IOrder record, @Param("example") IOrderExample example);

    int updateByPrimaryKeySelective(IOrder record);

    int updateByPrimaryKey(IOrder record);

    List<IOrder> listByCondition(IOrder order);

    /*查询所有*/
    List<IOrder> listAll();
}