package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IDetailtype;
import com.shixun.ihome.publicservice.pojo.IDetailtypeExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDetailtypeMapper {
    int countByExample(IDetailtypeExample example);

    int deleteByExample(IDetailtypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IDetailtype record);

    int insertSelective(IDetailtype record);

    List<IDetailtype> selectByExample(IDetailtypeExample example);

    IDetailtype selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IDetailtype record, @Param("example") IDetailtypeExample example);

    int updateByExample(@Param("record") IDetailtype record, @Param("example") IDetailtypeExample example);

    int updateByPrimaryKeySelective(IDetailtype record);

    int updateByPrimaryKey(IDetailtype record);

    IDetailtype selectByid(int typeid);
}