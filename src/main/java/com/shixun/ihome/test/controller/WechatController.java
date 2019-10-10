package com.shixun.ihome.test.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import com.shixun.ihome.test.service.WechatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringEscapeUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@Controller

public class WechatController {
    @Autowired
    private WechatService wechatService;

    @ResponseBody
    @RequestMapping("getCode")
    public int getOpenid(@RequestBody JSONObject getcode) {

        String code=getcode.getString("getcode");
//        code = StringEscapeUtils.unescapeJava(code);
//        JSONObject jsonObject= JSON.parseObject(code);
//        String data = jsonObject.getString("data");
//        JSONObject jsondata= JSON.parseObject(data);
//        String token = jsondata.getString("getcode");

        System.out.println(code);
        String WX_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

        String requestUrl = WX_URL.replace("APPID","wx28d20808cea0c171").
                replace("SECRET","834ac5963ea499f9ac8dd4ff2ae59e87").replace("JSCODE",code).
                replace("authorization_code","authorization_code");


        String  returnvalue=GET(requestUrl);
        System.out.println(requestUrl);
        System.out.println(returnvalue);

        JSONObject convertvalue=new JSONObject();
        convertvalue=(JSONObject) JSON.parse(returnvalue);


        System.out.println("return openid is ："+(String)convertvalue.get("openid"));
        System.out.println("return sessionkey is ："+(String)convertvalue.get("session_key"));

        String openid=(String) convertvalue.get("openid");
        String sessionkey=(String) convertvalue.get("session_key");

        int existence=wechatService.wechatlogin(openid);

        return existence;



    }


    public  String GET(String url) {
        String result = "";
        BufferedReader in = null;
        InputStream is = null;
        InputStreamReader isr = null;
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.connect();
            Map<String, List<String>> map = conn.getHeaderFields();
            is = conn.getInputStream();
            isr = new InputStreamReader(is);
            in = new BufferedReader(isr);
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            // 异常记录
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (is != null) {
                    is.close();
                }
                if (isr != null) {
                    isr.close();
                }
            } catch (Exception e2) {
                // 异常记录
            }
        }
        return result;
    }
}
