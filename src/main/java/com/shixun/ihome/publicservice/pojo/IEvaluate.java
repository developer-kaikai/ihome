package com.shixun.ihome.publicservice.pojo;

public class IEvaluate {
    private Integer id;

    private Integer orderId;

    private Integer qualityValuation;

    private Integer attitudeValuation;

    private String describe;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getQualityValuation() {
        return qualityValuation;
    }

    public void setQualityValuation(Integer qualityValuation) {
        this.qualityValuation = qualityValuation;
    }

    public Integer getAttitudeValuation() {
        return attitudeValuation;
    }

    public void setAttitudeValuation(Integer attitudeValuation) {
        this.attitudeValuation = attitudeValuation;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }
}