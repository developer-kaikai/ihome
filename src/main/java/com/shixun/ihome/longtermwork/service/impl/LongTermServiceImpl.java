package com.shixun.ihome.longtermwork.service.impl;

import com.shixun.ihome.longtermwork.service.LongTermService;
import com.shixun.ihome.publicservice.mapper.IOrderLongMapper;
import com.shixun.ihome.publicservice.mapper.IOrderMapper;
import com.shixun.ihome.publicservice.pojo.IOrder;
import com.shixun.ihome.publicservice.pojo.IOrderLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LongTermServiceImpl implements LongTermService {



    @Autowired
    private IOrderMapper orderMapper;
    @Autowired
    private IOrderLongMapper orderLongMapper;

    @Override
    public boolean addOrder(IOrder order) {
        orderMapper.insert(order);
        return true;
    }
    @Override
    public boolean cancelOrder(int id) {
        IOrder order =new IOrder();
        order.setId(id);
        order.setState(1);
        orderMapper.updateByPrimaryKeySelective(order);
        return true;
    }

    @Override
    public boolean confirmOrder(int id) {
        IOrder order =new IOrder();
        order.setId(id);
        order.setState(4);
        orderMapper.updateByPrimaryKeySelective(order);
        return true;
    }
}
