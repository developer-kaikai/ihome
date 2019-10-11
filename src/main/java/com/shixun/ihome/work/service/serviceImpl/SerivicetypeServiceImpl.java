package com.shixun.ihome.work.service.serviceImpl;

import com.shixun.ihome.publicservice.mapper.IDetailtypeMapper;
import com.shixun.ihome.publicservice.mapper.IServicetypeMapper;
import com.shixun.ihome.publicservice.pojo.IDetailtype;
import com.shixun.ihome.publicservice.pojo.IServicetype;
import com.shixun.ihome.work.service.ServicetypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerivicetypeServiceImpl implements ServicetypeService {
    @Autowired
    private IServicetypeMapper iServicetypeMapper;
    @Autowired
    private IDetailtypeMapper iDetailtypeMapper;

    @Override
    public IServicetype selectByid(int serviceid) {

        return iServicetypeMapper.selectByPrimaryKey(serviceid);
    }


    @Override
    public IDetailtype selectBytypeid(int typeid) {

        return iDetailtypeMapper.selectByid(typeid);
    }

    @Override
    public List<IDetailtype> selectByname(String typename) {

        return iDetailtypeMapper.selectByname(typename);
    }

    @Override
    public List<IDetailtype> selectByServicetypeid(int serviceid) {
        return iDetailtypeMapper.selectByServicetypeid(serviceid);
    }
}
