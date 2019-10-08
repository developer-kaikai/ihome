package com.shixun.ihome.hourwork.aop;

import com.shixun.ihome.publicservice.mapper.IRecordMapper;
import com.shixun.ihome.publicservice.pojo.IRecord;
import com.shixun.ihome.publicservice.util.Qutil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Aspect
@Component
public class Record {
    @Autowired
    private IRecordMapper iRecordMapper;

    @Pointcut("execution(public * com.shixun.ihome.hourwork.service.*.delete*(..))")
    public void delete(){

    }
    @Pointcut("execution(public * com.shixun.ihome.hourwork.service.*.add*(..))")
    public void add(){

    }
    @Pointcut("execution(public * com.shixun.ihome.hourwork.service.*.update*(..))")
    public void update(){

    }

    @Around("update()")
    public Object doupdateReturning(ProceedingJoinPoint point) throws Throwable {

        Object [] objects = point.getArgs();

        String sianature = point.getSignature().toString();
        String table = Qutil.getTableName(sianature, 1);

        String newrecord = objects[0].toString();
        String oldrecord = objects[1].toString();
        Object result = null;
        try {
            result = point.proceed();
        }catch (Exception e){
            e.printStackTrace();
        }
        IRecord iRecord = Qutil.createRecord(iRecordMapper.UPDATED, (String)objects[2], table, oldrecord.toString(), newrecord.toString());
        iRecordMapper.insert(iRecord);
        return result;
    }

    @Around("delete()")
    public Object doAfterReturning(ProceedingJoinPoint point) throws Throwable {

        Object[] objects = point.getArgs();
        String record = objects[0].toString();
        String byWho = objects[1].toString();
        String sinature = point.getSignature().toString();
        String table = Qutil.getTableName(sinature, 2);
        Object result = null;
        try{
            result = point.proceed();
        }catch (Exception e){
            e.printStackTrace();
        }
        IRecord iRecord = Qutil.createRecord(iRecordMapper.DELETED, byWho, table, record, "已删除");
        iRecordMapper.insert(iRecord);

        return result;
    }

    @Around("add()")
    public Object doaddReturning(ProceedingJoinPoint point) throws Throwable {
        Object [] objects = point.getArgs();

        String sianature = point.getSignature().toString();
        String table = Qutil.getTableName(sianature, 0);

        String record = objects[0].toString();
        String bywho = objects[1].toString();
        Object result = null;
        try {
            result = point.proceed();
        }catch (Exception e){
            e.printStackTrace();
        }
        IRecord iRecord = Qutil.createRecord(iRecordMapper.INSERT, bywho, table, "", record);
        iRecordMapper.insert(iRecord);
        return result;
    }
}

