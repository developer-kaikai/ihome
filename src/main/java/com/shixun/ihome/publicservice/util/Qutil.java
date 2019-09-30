package com.shixun.ihome.publicservice.util;


import com.shixun.ihome.publicservice.pojo.IRecord;

import java.util.Date;

public class Qutil {
    /**
     * 快速创建记录工具
     * @param status    状态 0：创建 1：修改 2：删除
     * @param byWho     记录人是谁
     * @param tableName 记录的表叫什么
     * @param oldRecord 旧记录（插入没有就记录）
     * @param newRecord 新记录（删除记录默认删除还是什么都可以）
     * @return record记录
     */
    public static IRecord createRecord(int status,String byWho, String tableName,String oldRecord, String newRecord){
        IRecord iRecord = new IRecord();
        iRecord.setBytime(new Date());
        iRecord.setStatus(status);
        iRecord.setTableName(tableName);
        iRecord.setOldContent(oldRecord);
        iRecord.setNewContent(newRecord);
        iRecord.setBywho(byWho);
        return iRecord;
    }

    /**
     * 字符串转换成时间表能识别的int
     * @param s 时间表字符串"01010111"之类的
     * @return  时间表代表的数字
     */
    public static Integer string2timer(String s){
        Integer timer = null;
        try {
            timer =  Integer.parseInt(s, 2);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return timer;
    }

    /**
     * 计算两日期之间的天数
     * @param newdate   新的日期
     * @param olddate   旧的日期
     * @return  两日期的天数
     */
    public static Integer consumDays(Date newdate, Date olddate){
        int days = 0;

        if (newdate == null || olddate == null){
            return 0;
        }

        days = (int) ((newdate.getTime() - olddate.getTime())/ 24 * 3600 * 1000);



        return days;
    }
}
