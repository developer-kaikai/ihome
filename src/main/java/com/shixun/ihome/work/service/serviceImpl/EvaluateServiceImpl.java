package com.shixun.ihome.work.service.serviceImpl;

import com.shixun.ihome.publicservice.mapper.IEvaluateMapper;
import com.shixun.ihome.publicservice.pojo.IEvaluate;
import com.shixun.ihome.work.service.EvaluateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EvaluateServiceImpl implements EvaluateService {
    @Autowired
    private IEvaluateMapper iEvaluateMapper;

    @Override
    public List<IEvaluate> listAll(int userid) {
        return iEvaluateMapper.listByid(userid);
    }

    @Override
    public List<IEvaluate> listbystaff(int id) {
        return iEvaluateMapper.listBystaffid(id);
    }
}
