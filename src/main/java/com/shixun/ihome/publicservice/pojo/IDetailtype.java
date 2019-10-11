package com.shixun.ihome.publicservice.pojo;

import java.io.Serializable;

public class IDetailtype implements Serializable {
    private Integer id;

    private String typename;

    private Integer servicetpyeId;

    private String ddescribe;

    private String chargeType;

    private String comm;

    private String explains;

    private IServiceItem iServiceItem;

    public IServiceItem getiServiceItem() {
        return iServiceItem;
    }

    public void setiServiceItem(IServiceItem iServiceItem) {
        this.iServiceItem = iServiceItem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename == null ? null : typename.trim();
    }

    public Integer getServicetpyeId() {
        return servicetpyeId;
    }

    public void setServicetpyeId(Integer servicetpyeId) {
        this.servicetpyeId = servicetpyeId;
    }

    public String getDdescribe() {
        return ddescribe;
    }

    public void setDdescribe(String ddescribe) {
        this.ddescribe = ddescribe == null ? null : ddescribe.trim();
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType == null ? null : chargeType.trim();
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm == null ? null : comm.trim();
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains == null ? null : explains.trim();
    }
}