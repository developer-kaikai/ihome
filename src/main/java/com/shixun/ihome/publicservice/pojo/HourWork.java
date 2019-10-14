package com.shixun.ihome.publicservice.pojo;


import java.util.List;

public class HourWork {
    private List<IStaff> staffs;
    private IOrder order;
    private Integer timer;


    public List<IStaff> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<IStaff> staffs) {
        this.staffs = staffs;
    }

    public IOrder getOrder() {
        return order;
    }

    public void setOrder(IOrder order) {
        this.order = order;
    }

    public Integer getTimer() {
        return timer;
    }

    public void setTimer(Integer timer) {
        this.timer = timer;
    }

    @Override
    public String toString() {
        return "HourWork{" +
                "staffs=" + staffs +
                ", order=" + order +
                ", timer='" + timer + '\'' +
                '}';
    }
}
