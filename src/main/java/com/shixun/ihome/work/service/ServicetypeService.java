package com.shixun.ihome.work.service;

import com.shixun.ihome.publicservice.pojo.IDetailtype;
import com.shixun.ihome.publicservice.pojo.IServicetype;

public interface ServicetypeService {
    /*服务大类详情页面数据*/
    IServicetype selectByid(int serviceid);

    /*服务详细类页面数据*/
    IDetailtype selectBytypeid(int typeid);



}
