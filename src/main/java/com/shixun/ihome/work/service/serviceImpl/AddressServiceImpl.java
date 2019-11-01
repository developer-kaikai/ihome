package com.shixun.ihome.work.service.serviceImpl;

import com.shixun.ihome.publicservice.mapper.IUserDetailMapper;
import com.shixun.ihome.publicservice.pojo.IUserDetail;
import com.shixun.ihome.work.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private IUserDetailMapper userDetailMapper;
    @Override
    public IUserDetail getOne(int id) {
        return userDetailMapper.selectByPrimaryKey(id);
    }
}
