package com.shixun.ihome.publicservice.pojo;

import java.util.Date;

public class ITimer {

    private Integer id;

    private Long timer;

    private Date updateTime;

    private Integer staffId;

    private IStaff iStaff;

    public IStaff getiStaff() {
        return iStaff;
    }

    public void setiStaff(IStaff iStaff) {
        this.iStaff = iStaff;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ITimer{" +
                "id=" + id +
                ", timer=" + timer +
                ", updateTime=" + updateTime +
                ", staffId=" + staffId +
                ", iStaff=" + iStaff +
                '}';
    }

    public Long getTimer() {
        return timer;
    }

    public void setTimer(Long timer) {
        this.timer = timer;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }
}