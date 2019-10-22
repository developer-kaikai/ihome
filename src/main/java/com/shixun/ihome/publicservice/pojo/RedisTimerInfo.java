package com.shixun.ihome.publicservice.pojo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class RedisTimerInfo  implements Serializable {
    //日期（星期几）
    @JsonProperty(value = "label")
    private String date;
    @JsonProperty(value = "value")
    private String value;
    //时间表
    @JsonProperty(value = "children")
    private List<LabelValue> timer;


    public List<LabelValue> getTimer() {
        return timer;
    }

    public void setTimer(List<LabelValue> timer) {
        this.timer = timer;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "RedisTimerInfo{" +
                "date='" + date + '\'' +
                ", value='" + value + '\'' +
                ", timer=" + timer +
                '}';
    }
}


