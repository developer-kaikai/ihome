package com.shixun.ihome.work.service.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shixun.ihome.publicservice.mapper.IUserDetailMapper;
import com.shixun.ihome.publicservice.mapper.IUserMapper;
import com.shixun.ihome.publicservice.pojo.IUser;


import com.shixun.ihome.publicservice.pojo.IUserDetail;
import com.shixun.ihome.publicservice.pojo.IUserDetailExample;
import com.shixun.ihome.publicservice.pojo.IUserExample;
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
    public IUserDetail selectUserDefaultAddress(int id) {
        IUserDetail iUserDetail=iUserDetailMapper.selectUserDefaultAddress(id);
        return iUserDetail;
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

    @Override
    public boolean updateUserDetail(IUserDetail iUserDetail) {
        iUserDetailMapper.updateByPrimaryKeySelective(iUserDetail);
        return true;
    }

    @Override
    public String getOpenId(int userId) {
        IUser user = iUserMapper.getOpenId(userId);
        return user.getWeixin().getOpenId();
    }


    @Override
    public JSONArray getWeiXinId(String phone) {
        IUserExample iUserExample = new IUserExample();
        IUserExample.Criteria criteria = iUserExample.createCriteria();
        criteria.andPhoneLike(phone);
        List<IUser> users = iUserMapper.selectByExample(iUserExample);
        JSONArray arr = new JSONArray(users.size());
        for (IUser user: users ) {
            JSONObject obj = new JSONObject(2);
            obj.put("label",user.getPhone());
            obj.put("value",user.getWeixinId());
            arr.add(obj);
        }
        return arr;
    }

    @Override
    public PageInfo<IUser> selectUserByCondition(IUser user, int pageNum, int pageSize) {
        IUserExample userExample = new IUserExample();
        IUserExample.Criteria criteria = userExample.createCriteria();
        if(user.getName() != null){
            criteria.andNameLike(user.getName() + "%");
        }
        if(user.getPhone()!= null){
            criteria.andPhoneLike(user.getPhone() + "%");
        }
        userExample.setOrderByClause("id");
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<IUser> lists = new PageInfo<>(iUserMapper.selectByExample(userExample));

        return lists;
    }

    @Override
    public IUserDetail getOrderDetail(int orderId) {
        return iUserDetailMapper.selectByPrimaryKey(orderId);
    }
}
