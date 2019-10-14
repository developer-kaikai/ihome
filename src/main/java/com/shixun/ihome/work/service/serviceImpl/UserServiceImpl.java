package com.shixun.ihome.work.service.serviceImpl;

import com.shixun.ihome.publicservice.mapper.IUserMapper;
import com.shixun.ihome.publicservice.pojo.IUser;


import com.shixun.ihome.work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private IUserMapper iUserMapper;
    @Override
    public List<IUser> selectUsers(IUser iUser) {
        List<IUser> usersList=iUserMapper.selectUsers(iUser);
        return usersList;
    }
}
