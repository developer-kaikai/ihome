package com.shixun.ihome.publicservice.pojo;

import java.io.Serializable;
import java.util.List;

public class RedisTimerInfo  implements Serializable {
    //日期（星期几）
    String date;
    //时间表
    List<String> timer;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getTimer() {
        return timer;
    }

    public void setTimer(List<String> timer) {
        this.timer = timer;
    }

    @Override
    public String toString() {
        return "RedisTimerInfo{" +
                "date='" + date + '\'' +
                ", timer=" + timer +
                '}';
    }
}
