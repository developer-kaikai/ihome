package com.shixun.ihome.work.service.serviceImpl;

import com.shixun.ihome.publicservice.mapper.IUserDetailMapper;
import com.shixun.ihome.publicservice.mapper.IUserMapper;
import com.shixun.ihome.publicservice.pojo.IUser;


import com.shixun.ihome.publicservice.pojo.IUserDetail;
import com.shixun.ihome.work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private IUserMapper iUserMapper;
    @Autowired
    private IUserDetailMapper iUserDetailMapper;
    @Override
    public List<IUser> selectUsers(IUser iUser) {
        List<IUser> usersList=iUserMapper.selectUsers(iUser);
        return usersList;
    }

    @Override
    public boolean addUserDetail(IUserDetail iUserDetail) {
        iUserDetailMapper.insert(iUserDetail);
        return true;
    }

    @Override
    public List<IUserDetail> selectUserAddress(int id) {
        List<IUserDetail> addressList=iUserDetailMapper.selectUserAddress(id);
        return addressList;
    }
}
