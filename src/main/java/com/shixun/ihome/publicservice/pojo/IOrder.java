package com.shixun.ihome.publicservice.pojo;

import java.util.Date;

public class IOrder {
    private Integer id;

    private Integer userId;
    private IUser user;

    private Integer useraddressId;
    private IUserDetail userDetail;

    private Integer detailtypeId;
    private IDetailtype detailtype;

    private Date startTime;

    private Double price;

    private Date orderTime;

    private Date finalyTime;

    private Integer state;

    private String comm;

    public IUser getUser() {
        return user;
    }

    public void setUser(IUser user) {
        this.user = user;
    }

    public IUserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(IUserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public IDetailtype getDetailtype() {
        return detailtype;
    }

    public void setDetailtype(IDetailtype detailtype) {
        this.detailtype = detailtype;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUseraddressId() {
        return useraddressId;
    }

    public void setUseraddressId(Integer useraddressId) {
        this.useraddressId = useraddressId;
    }

    public Integer getDetailtypeId() {
        return detailtypeId;
    }

    public void setDetailtypeId(Integer detailtypeId) {
        this.detailtypeId = detailtypeId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getFinalyTime() {
        return finalyTime;
    }

    public void setFinalyTime(Date finalyTime) {
        this.finalyTime = finalyTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm == null ? null : comm.trim();
    }
}