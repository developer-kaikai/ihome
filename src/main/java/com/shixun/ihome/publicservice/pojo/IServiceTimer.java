package com.shixun.ihome.publicservice.pojo;

import java.util.Date;

public class IServiceTimer {
    private Integer id;

    private Integer servicelid;

    private Integer staffnum;

    private String date;

    private Date updatetimer;

    private String index;

    private String num;

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

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index == null ? null : index.trim();
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
    }
}