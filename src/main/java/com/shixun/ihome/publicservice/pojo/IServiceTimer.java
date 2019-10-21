package com.shixun.ihome.publicservice.pojo;

import java.util.Date;

public class IServiceTimer {
    private Integer id;

    private Integer servicelid;

    private Integer staffnum;

    private String date;

    private Date updatetimer;

    private Double index;

    private Integer num;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServicelid() {
        return servicelid;
    }

    public void setServicelid(Integer servicelid) {
        this.servicelid = servicelid;
    }

    public Integer getStaffnum() {
        return staffnum;
    }

    public void setStaffnum(Integer staffnum) {
        this.staffnum = staffnum;
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

    public Double getIndex() {
        return index;
    }

    public void setIndex(Double index) {
        this.index = index;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}