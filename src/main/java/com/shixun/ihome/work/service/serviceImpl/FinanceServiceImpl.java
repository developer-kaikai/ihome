package com.shixun.ihome.work.service.serviceImpl;

import com.shixun.ihome.publicservice.mapper.ISalaryMapper;
import com.shixun.ihome.publicservice.mapper.ISalaryRateMapper;
import com.shixun.ihome.publicservice.pojo.ISalary;
import com.shixun.ihome.publicservice.pojo.ISalaryRate;
import com.shixun.ihome.work.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FinanceServiceImpl implements FinanceService {
    @Autowired
    private ISalaryMapper iSalaryMapper;
    @Autowired
    private ISalaryRateMapper iSalaryRateMapper;




    @Override
    public List<Map<String, Object>> addsalary() {
        return iSalaryMapper.addsalary();
    }

    @Override
    public List<ISalary> salarynow(List<Map<String, Object>> map) {
        int i=iSalaryMapper.salarynow(map);

        return iSalaryMapper.listall();
    }


    @Override
    public Boolean modifySalary(int typeid,double bonusrate, double rolatyrate) {
        iSalaryRateMapper.modifyrate(typeid,bonusrate,rolatyrate);
        return true;
    }
}
