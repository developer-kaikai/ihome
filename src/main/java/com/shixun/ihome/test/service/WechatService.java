package com.shixun.ihome.test.service;

public interface WechatService {
    int wechatlogin(String openid);

    int userid(String openid);

    String havaphone(int userid);


    Boolean addphone(int userid,String phone);
}
