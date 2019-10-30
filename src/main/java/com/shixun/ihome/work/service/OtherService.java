package com.shixun.ihome.work.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shixun.ihome.publicservice.pojo.IPosition;

import java.util.List;

public interface OtherService {
    //获取职位列表
    JSONArray getPositions();
}
