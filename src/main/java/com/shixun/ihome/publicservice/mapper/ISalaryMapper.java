package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.ISalary;
import com.shixun.ihome.publicservice.pojo.ISalaryExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ISalaryMapper {
    int countByExample(ISalaryExample example);

    int deleteByExample(ISalaryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ISalary record);

    int insertSelective(ISalary record);

    List<ISalary> selectByExample(ISalaryExample example);

    ISalary selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ISalary record, @Param("example") ISalaryExample example);

    int updateByExample(@Param("record") ISalary record, @Param("example") ISalaryExample example);

    int updateByPrimaryKeySelective(ISalary record);

    int updateByPrimaryKey(ISalary record);



    List<Map<String,Object>> addsalary();
    /*批量更新工资*/
    int salarynow(List<Map<String,Object>> map);

    List<ISalary> listall();

}