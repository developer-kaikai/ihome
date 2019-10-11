package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IPosition;
import com.shixun.ihome.publicservice.pojo.IPositionExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPositionMapper {
    int countByExample(IPositionExample example);

    int deleteByExample(IPositionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IPosition record);

    int insertSelective(IPosition record);

    List<IPosition> selectByExample(IPositionExample example);

    IPosition selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IPosition record, @Param("example") IPositionExample example);

    int updateByExample(@Param("record") IPosition record, @Param("example") IPositionExample example);

    int updateByPrimaryKeySelective(IPosition record);

    int updateByPrimaryKey(IPosition record);
}