package com.shixun.ihome.work.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shixun.ihome.json.Result;
import com.shixun.ihome.json.ResultType;
import com.shixun.ihome.publicservice.pojo.IStaff;
import com.shixun.ihome.publicservice.pojo.IUser;
import com.shixun.ihome.test.service.WechatService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("json")
public class WechatController {
    @Autowired
    private WechatService wechatService;

    @ResponseBody
    @RequestMapping("Code")
    public void getOpen(@RequestBody JSONObject getcode, HttpServletResponse response)throws IOException {

        String code=getcode.getString("getcode");


        String WX_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

        String requestUrl = WX_URL.replace("APPID","wx28d20808cea0c171").
                replace("SECRET","834ac5963ea499f9ac8dd4ff2ae59e87").replace("JSCODE",code).
                replace("authorization_code","authorization_code");


        String  returnvalue=GET(requestUrl);


        JSONObject convertvalue=new JSONObject();
        convertvalue=(JSONObject) JSON.parse(returnvalue);



        String openid=(String) convertvalue.get("openid");
        System.out.println("111111111"+openid);
        String sessionkey=(String) convertvalue.get("session_key");

        int existence=wechatService.wechatlogin(openid);
        Map map=new HashMap();
        if(existence==4) {
            int userid = wechatService.userid(openid);
            IUser user = wechatService.selectuser(userid);
            String havephone=wechatService.havaphone(userid);
            map.put("havephone",havephone);
            map.put("userid",userid);
            map.put("existence",existence);
          //  map.put("user",user);
        }else {
            IStaff user=wechatService.selectbyopenid(openid);
            int userid=user.getId();
            String havephone="true";
            map.put("havephone",havephone);
            map.put("userid",userid);
            map.put("existence",existence);
           // map.put("user",user);
        }



        response.setContentType("application/json;charset=utf-8");

        String json ;
        json = Result.build(ResultType.Success).appendData("map", map).convertIntoJSON();
        response.getWriter().write(json);

    }



    @RequestMapping(value="/updateOrderState",method = RequestMethod.POST)
    public Boolean addphone(@RequestBody JSONObject name){
        int userid=name.getInteger("userid");
        String phone=name.getString("phone");
        Boolean sucess=wechatService.addphone(userid,phone);
        return true;

    }

    @ResponseBody
    @RequestMapping("getCode")
    public void getOpenid(@RequestBody JSONObject getcode, HttpServletResponse response)throws IOException {

        String code=getcode.getString("getcode");
        System.out.println(code);
      JSONObject userInfo=getcode.getJSONObject("userInfo");
       System.out.println(userInfo);
       String name=userInfo.getString("nickName");
       int gender=userInfo.getInteger("gender");
       String country=userInfo.getString("country");
       String language=userInfo.getString("language");
       String province=userInfo.getString("province");
       String city=userInfo.getString("city");

        String WX_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

        String requestUrl = WX_URL.replace("APPID","wx28d20808cea0c171").
                replace("SECRET","834ac5963ea499f9ac8dd4ff2ae59e87").replace("JSCODE",code).
                replace("authorization_code","authorization_code");


        String  returnvalue=GET(requestUrl);


        JSONObject convertvalue=new JSONObject();
        convertvalue=(JSONObject) JSON.parse(returnvalue);



        String openid=(String) convertvalue.get("openid");
        System.out.println("111111111"+openid);
        String sessionkey=(String) convertvalue.get("session_key");


        int userid=wechatService.userid(openid);
        Boolean success=wechatService.addusernews(userid, name, gender, country, language,province,city);
        System.out.println(success);




        Map map=new HashMap();
        map.put("success",success);

        String json ;
        json = Result.build(ResultType.Success).appendData("map", map).convertIntoJSON();
        response.getWriter().write(json);

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
