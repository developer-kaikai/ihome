package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IUserDetail;
import com.shixun.ihome.publicservice.pojo.IUserDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IUserDetailMapper {
    int countByExample(IUserDetailExample example);

    int deleteByExample(IUserDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IUserDetail record);

    int insertSelective(IUserDetail record);

    List<IUserDetail> selectByExample(IUserDetailExample example);

    IUserDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IUserDetail record, @Param("example") IUserDetailExample example);

    int updateByExample(@Param("record") IUserDetail record, @Param("example") IUserDetailExample example);

    int updateByPrimaryKeySelective(IUserDetail record);

    int updateByPrimaryKey(IUserDetail record);
}