package com.shixun.ihome.test.service;

import com.shixun.ihome.publicservice.pojo.IStaff;
import com.shixun.ihome.publicservice.pojo.IUser;

public interface WechatService {
    int wechatlogin(String openid);

    int userid(String openid);

    String havaphone(int userid);


    Boolean addphone(int userid,String phone);

    Boolean addusernews(int userid,String name,int gender,String country,String language,String province,String city);

    IUser selectuser(int userid);

    IStaff selectbyopenid(String openid);
}
