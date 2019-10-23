package com.shixun.ihome.publicservice.pojo;

import java.io.Serializable;
import java.util.List;

public class IUser implements Serializable {
    private Integer id;

    private Integer weixinId;

    private String phone;

    private String name;

    private IWeixin weixin;

    private List<IUserDetail> userDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(Integer weixinId) {
        this.weixinId = weixinId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public List<IUserDetail> getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(List<IUserDetail> userDetail) {
        this.userDetail = userDetail;
    }


    public IWeixin getWeixin() {
        return weixin;
    }

    public void setWeixin(IWeixin weixin) {
        this.weixin = weixin;
    }
}