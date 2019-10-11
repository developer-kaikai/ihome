package com.shixun.ihome.publicservice.pojo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class HourWork {
    private List<IStaff> staffs;
    @ApiModelProperty(name = "order", value = "订单")
    private IOrder order;
    @ApiModelProperty(name = "tiemr", value = "时间表（别管，发回来就好）")
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
