package com.shixun.ihome.test.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.shixun.ihome.publicservice.mapper.IUserMapper;
import com.shixun.ihome.publicservice.mapper.IWeixinMapper;
import com.shixun.ihome.publicservice.pojo.*;
import com.shixun.ihome.test.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WechatServiceImpl implements WechatService {
    @Autowired
    private IWeixinMapper iWeixinMapper;
    @Autowired
    private IUserMapper iUserMapper;



    @Override
    public int wechatlogin(String openid) {
        IWeixin iWeixin1=iWeixinMapper.selectByopenid(openid);
        if ("".equals(iWeixin1)||iWeixin1==null) {
            IWeixin iWeixin = new IWeixin();
            iWeixin.setOpenId(openid);
            iWeixin.setPositionId(4);
            iWeixinMapper.insert(iWeixin);

            IWeixinExample example=new IWeixinExample();
            IWeixinExample.Criteria criteria=example.createCriteria();
            criteria.andOpenIdEqualTo(openid);
            List<IWeixin> iWeixinList=iWeixinMapper.selectByExample(example);
            int id=iWeixinList.get(0).getId();
            IUser user=new IUser();
            user.setWeixinId(id);
            iUserMapper.insert(user);
            return iWeixin.getPositionId();
        }
        else {
            return iWeixin1.getPositionId();
        }
    }

    @Override
    public int userid(String openid) {
        IWeixinExample example=new IWeixinExample();
        IWeixinExample.Criteria criteria=example.createCriteria();
        criteria.andOpenIdEqualTo(openid);
        List<IWeixin> iWeixinList=iWeixinMapper.selectByExample(example);
        int id=iWeixinList.get(0).getId();

        IUserExample example1=new IUserExample();
        IUserExample.Criteria criteria1=example1.createCriteria();
        criteria1.andWeixinIdEqualTo(id);

        List<IUser> userList=iUserMapper.selectByExample(example1);
        int userid=userList.get(0).getId();
        return id;


    }

    @Override
    public Boolean havaphone(int userid) {
        IUser user=iUserMapper.selectByPrimaryKey(userid);

        System.out.println(user.getPhone());
        if("".equals(user.getPhone())||user.getPhone()==null){
            return false;
        }else{
            return true;
        }
    }


    @Override
    public Boolean addphone(int userid, String phone) {
        IUser user=iUserMapper.selectByPrimaryKey(userid);
        user.setPhone(phone);
        iUserMapper.updateByPrimaryKeySelective(user);
        return true;
    }
}
