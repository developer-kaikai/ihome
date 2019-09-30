package com.shixun.ihome.publicservice.util;


import com.shixun.ihome.publicservice.pojo.IRecord;

import java.util.Date;

public class Qutil {
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

}
