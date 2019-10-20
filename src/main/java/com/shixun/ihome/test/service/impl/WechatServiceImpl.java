package com.shixun.ihome.test.service.impl;

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
        List<IWeixin> iWeixin1=iWeixinMapper.selectByopenid(openid);
        if (iWeixin1.size()==0) {
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
            return iWeixin1.get(0).getPositionId();
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
}
