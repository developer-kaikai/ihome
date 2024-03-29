package com.shixun.ihome.publicservice.util;


import com.shixun.ihome.publicservice.pojo.IRecord;
import org.omg.SendingContext.RunTime;

import javax.xml.bind.SchemaOutputResolver;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    //文件删除
    public static void deleteFile(String path){
        //检测路径是否为空
        if(path.equals("")||path.equals(null)){
            return;
        }
        File file = new File(path);
        if(file.exists()){
            file.delete();
        }
    }


    public static Date toDate(String s){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try{
            date = sdf.parse(s);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }
    public static Date toDateTime(String s){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try{
            date = sdf.parse(s);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
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

        newdate = removeTimer(newdate);
        olddate = removeTimer(olddate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(olddate);
        long time2 = cal.getTimeInMillis();
        long between_days=(Math.abs(time1-time2))/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 判断日期是否时同一天
     * @param date1 日期一
     * @param date2 日期二
     * @return 如果是同一天就是true否则false
     */
    public static boolean assertDate(Date date1, Date date2){
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)){
            return true;
        }
        return false;
    }

    public final static int MINUTE = 60 * 1000;
    public final static int SECONE = 1000;
    public final static  int HOUR = 1000 * 60 * 60;

    public static boolean assertTimer(Date date1, Date date2, int cal_type, int num ){
        long diff = Math.abs(date1.getTime() - date2.getTime());
        return (diff/cal_type) < num;
    }

    /**
     * 移除日期的时间部分
     * @param date
     * @return
     */
    public static Date removeTimer(Date date){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(sdf.format(date));
        }catch (Exception e){
            throw new RuntimeException("时间转换出错");
        }
        return date;
    }

    //时间表专用，计算时间占用哪一个时间段
    public static int getTimer(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //设置开始时间
        Calendar start = Calendar.getInstance();
        start.setTime(date);
        start.set(Calendar.HOUR_OF_DAY,8);
        start.set(Calendar.MINUTE,0);
        //设置结束时间
        Calendar end = Calendar.getInstance();
        end.setTime(date);
        end.set(Calendar.HOUR_OF_DAY,10);
        end.set(Calendar.MINUTE, 0);
        //开始计算
        int time = 0;
        for(int i = 0; i < 6; i++){
            if((start.before(calendar)||start.equals(calendar) )&& end.after(calendar)){

                break;
            }
            //添加时间
            time++;
            end.add(Calendar.HOUR_OF_DAY,2);
            start.add(Calendar.HOUR_OF_DAY,2);
        }
        if(time > 5){
            throw new RuntimeException();
        }
        return 1 << time;
    }


    public static boolean before(Date date){
        Calendar calendar = Calendar.getInstance();
        Calendar cdate = Calendar.getInstance();
        cdate.setTime(date);
        return calendar.before(cdate);
    }

    /**
     * 获取表的名称
     * @param s  sianature
     * @param status    0：插入，1：更新，2：删除
     * @return
     */
    public static String getTableName (String s, int status){
        String pattern = null;
        switch (status){
            case 1: pattern = "update(\\w*)Record";break;
            case 2: pattern = "delete(\\w*)Record";break;
            default:pattern = "add(\\w*)Record";break;
        }
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(s);
        String table = null;
        if (m.find()){
            table = "i_" + m.group(1).toLowerCase();
        }else{
            table = "未知表";
        }
        return table;
    }
}
