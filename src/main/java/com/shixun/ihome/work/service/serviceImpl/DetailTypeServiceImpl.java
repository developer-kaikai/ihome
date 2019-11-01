package com.shixun.ihome.work.service.serviceImpl;

import com.shixun.ihome.publicservice.mapper.IDetailtypeMapper;
import com.shixun.ihome.publicservice.pojo.IDetailtype;
import com.shixun.ihome.work.service.DetailTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailTypeServiceImpl implements DetailTypeService {
    @Autowired
    private IDetailtypeMapper detailtypeMapper;
    @Override
    public IDetailtype getOne(int id) {
        return detailtypeMapper.selectByPrimaryKey(id);
    }
}
