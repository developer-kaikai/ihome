package com.shixun.ihome.work.service.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shixun.ihome.publicservice.mapper.IPositionMapper;
import com.shixun.ihome.publicservice.pojo.IPosition;
import com.shixun.ihome.publicservice.pojo.IPositionExample;
import com.shixun.ihome.work.service.OtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OtherServiceImpl implements OtherService {
    @Autowired
    private IPositionMapper positionMapper;

    @Override
    public JSONArray  getPositions() {
        IPositionExample example = new IPositionExample();
        example.setOrderByClause("id");
        List<IPosition> lists = positionMapper.selectByExample(example);
        JSONArray arr = new JSONArray(lists.size());
        for (IPosition pos : lists) {
            JSONObject object = new JSONObject(2);
            object.put("value",pos.getId());
            object.put("label",pos.getPosition());
            arr.add(object);
        }
        return arr;
    }
}
