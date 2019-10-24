package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.ITool;
import com.shixun.ihome.publicservice.pojo.IToolExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IToolMapper {
    int countByExample(IToolExample example);

    int deleteByExample(IToolExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ITool record);

    int insertSelective(ITool record);

    List<ITool> selectByExample(IToolExample example);

    ITool selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ITool record, @Param("example") IToolExample example);

    int updateByExample(@Param("record") ITool record, @Param("example") IToolExample example);

    int updateByPrimaryKeySelective(ITool record);

    int updateByPrimaryKey(ITool record);

    List<ITool> selectByname(String name);

}