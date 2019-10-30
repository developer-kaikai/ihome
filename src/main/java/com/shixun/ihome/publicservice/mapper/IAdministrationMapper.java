package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IAdministration;
import com.shixun.ihome.publicservice.pojo.IAdministrationExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAdministrationMapper {
    int countByExample(IAdministrationExample example);

    int deleteByExample(IAdministrationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IAdministration record);

    int insertSelective(IAdministration record);

    List<IAdministration> selectByExample(IAdministrationExample example);

    IAdministration selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IAdministration record, @Param("example") IAdministrationExample example);

    int updateByExample(@Param("record") IAdministration record, @Param("example") IAdministrationExample example);

    int updateByPrimaryKeySelective(IAdministration record);

    int updateByPrimaryKey(IAdministration record);
}