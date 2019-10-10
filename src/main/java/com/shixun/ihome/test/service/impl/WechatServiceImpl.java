package com.shixun.ihome.test.service.impl;

import com.shixun.ihome.publicservice.mapper.IWeixinMapper;
import com.shixun.ihome.publicservice.pojo.IWeixin;
import com.shixun.ihome.test.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WechatServiceImpl implements WechatService {
    @Autowired
    private IWeixinMapper iWeixinMapper;

    @Override
    public int wechatlogin(String openid) {
        IWeixin iWeixin1=iWeixinMapper.selectByopenid(openid);
        if (iWeixin1==null) {

            IWeixin iWeixin = new IWeixin();
            iWeixin.setOpenId(openid);
            iWeixin.setPositionId(4);
            iWeixinMapper.insert(iWeixin);
            return iWeixin.getPositionId();
        }
        else {
            return iWeixin1.getPositionId();
        }
    }
}
