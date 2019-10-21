package com.shixun.ihome.publicservice.pojo;

import java.util.Date;

public class IDetailTimer {
    private Integer id;

    private Integer detailid;

    private Integer num;

    private String date;

    private Date updatetimer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDetailid() {
        return detailid;
    }

    public void setDetailid(Integer detailid) {
        this.detailid = detailid;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    public Date getUpdatetimer() {
        return updatetimer;
    }

    public void setUpdatetimer(Date updatetimer) {
        this.updatetimer = updatetimer;
    }
}