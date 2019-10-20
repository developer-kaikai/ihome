package com.shixun.ihome.publicservice.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class IRecord {
    private Integer id;

    private String bywho;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date bytime;

    private String tableName;

    private String oldContent;

    private String newContent;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBywho() {
        return bywho;
    }

    public void setBywho(String bywho) {
        this.bywho = bywho == null ? null : bywho.trim();
    }

    public Date getBytime() {
        return bytime;
    }

    public void setBytime(Date bytime) {
        this.bytime = bytime;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getOldContent() {
        return oldContent;
    }

    public void setOldContent(String oldContent) {
        this.oldContent = oldContent == null ? null : oldContent.trim();
    }

    public String getNewContent() {
        return newContent;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent == null ? null : newContent.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}