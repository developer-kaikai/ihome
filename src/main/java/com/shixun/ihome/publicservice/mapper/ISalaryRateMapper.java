package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.ISalaryRate;
import com.shixun.ihome.publicservice.pojo.ISalaryRateExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISalaryRateMapper {
    int countByExample(ISalaryRateExample example);

    int deleteByExample(ISalaryRateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ISalaryRate record);

    int insertSelective(ISalaryRate record);

    List<ISalaryRate> selectByExample(ISalaryRateExample example);

    ISalaryRate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ISalaryRate record, @Param("example") ISalaryRateExample example);

    int updateByExample(@Param("record") ISalaryRate record, @Param("example") ISalaryRateExample example);

    int updateByPrimaryKeySelective(ISalaryRate record);

    int updateByPrimaryKey(ISalaryRate record);

    int modifyrate(int typeid,double bonusrate,double rolatyrate);
}