package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IServicetype;
import com.shixun.ihome.publicservice.pojo.IServicetypeExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IServicetypeMapper {
    int countByExample(IServicetypeExample example);

    int deleteByExample(IServicetypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IServicetype record);

    int insertSelective(IServicetype record);

    List<IServicetype> selectByExample(IServicetypeExample example);

    IServicetype selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IServicetype record, @Param("example") IServicetypeExample example);

    int updateByExample(@Param("record") IServicetype record, @Param("example") IServicetypeExample example);

    int updateByPrimaryKeySelective(IServicetype record);

    int updateByPrimaryKey(IServicetype record);
}