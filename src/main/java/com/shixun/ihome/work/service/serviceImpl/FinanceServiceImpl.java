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




    /*计算提成*/
    @Override
    public List<Map<String, Object>> addsalary() {
        return iSalaryMapper.addsalary();
    }

    /*更新到数据库*/
    @Override
    public List<ISalary> salarynow(List<Map<String, Object>> map) {
        int i=iSalaryMapper.salarynow(map);

        return iSalaryMapper.listall();
    }

   /*修改提成率*/
    @Override
    public Boolean modifySalary(int typeid,double bonusrate, double rolatyrate) {
        iSalaryRateMapper.modifyrate(typeid,bonusrate,rolatyrate);
        return true;
    }

    @Override
    public List<ISalary> selectSalaryBymonth(Date date,List<Map<String, Object>> map) {
        Date date1=new Date();
        String year=String.format("%tY",date);
        String mon=String .format("%tm",date);
        String year1=String.format("%tY",date1);
        String mon1=String .format("%tm",date1);
        if(year.equals(year1)&&mon.equals(mon1)){
            List<ISalary> iSalaryList=iSalaryMapper.existMonth(date);
            if (iSalaryList.size()==0){
                System.out.println("查询的是本个月数据，数据库中无，先插入。。。。");
                iSalaryMapper.insertSalaryMonth(map);
                return  iSalaryMapper.existMonth(date);

            }else {
                System.out.println("查询的是本个月数据，数据库中有，已更新。。。。");
                iSalaryMapper.insertSalaryMonth(map);
                int i=iSalaryMapper.salarynow(map);
                return iSalaryMapper.listall();
            }

        }else {
            System.out.println("查询的是上个月数据，从数据库中读取。。。。");
            return iSalaryMapper.existMonth(date);

        }

    }

    @Override
    public boolean modifyBaseSalar(ISalary iSalary) {
        iSalaryMapper.updateByPrimaryKeySelective(iSalary);
        return true;
    }
}
