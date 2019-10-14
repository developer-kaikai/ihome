package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IUser;
import com.shixun.ihome.publicservice.pojo.IUserExample;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserMapper {
    int countByExample(IUserExample example);

    int deleteByExample(IUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IUser record);

    int insertSelective(IUser record);

    List<IUser> selectByExample(IUserExample example);

    IUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IUser record, @Param("example") IUserExample example);

    int updateByExample(@Param("record") IUser record, @Param("example") IUserExample example);

    int updateByPrimaryKeySelective(IUser record);

    int updateByPrimaryKey(IUser record);

    List<IUser> selectUsers(IUser iUser);
}