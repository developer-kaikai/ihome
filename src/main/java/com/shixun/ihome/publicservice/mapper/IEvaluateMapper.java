package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IEvaluate;
import com.shixun.ihome.publicservice.pojo.IEvaluateExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IEvaluateMapper {
    int countByExample(IEvaluateExample example);

    int deleteByExample(IEvaluateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IEvaluate record);

    int insertSelective(IEvaluate record);

    List<IEvaluate> selectByExample(IEvaluateExample example);

    IEvaluate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IEvaluate record, @Param("example") IEvaluateExample example);

    int updateByExample(@Param("record") IEvaluate record, @Param("example") IEvaluateExample example);

    int updateByPrimaryKeySelective(IEvaluate record);

    int updateByPrimaryKey(IEvaluate record);
}