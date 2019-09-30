package com.shixun.ihome.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config/pagehelper.properties")
@ConfigurationProperties(prefix="com.shixun.ihome")
public class IhomePagehelperConfig {



    private Integer pageSize;


    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
