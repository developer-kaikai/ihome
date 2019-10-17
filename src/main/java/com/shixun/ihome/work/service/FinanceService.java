package com.shixun.ihome.work.service;

import com.shixun.ihome.publicservice.pojo.ISalary;

import java.util.List;
import java.util.Date;
import java.util.Map;


public interface FinanceService {


    List<ISalary> salarynow(List<Map<String,Object>> map);

    List<Map<String,Object>> addsalary();

    Boolean modifySalary(int typeid,double bonusrate,double rolatyrate);

    List<ISalary> selectSalaryBymonth(Date date,List<Map<String, Object>> map);
}
