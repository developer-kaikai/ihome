package com.shixun.ihome.work.service;

import com.shixun.ihome.publicservice.pojo.IEvaluate;

import java.util.List;

public interface EvaluateService {
    List<IEvaluate> listAll(int userid);

    List<IEvaluate> listbystaff(int id);
}
