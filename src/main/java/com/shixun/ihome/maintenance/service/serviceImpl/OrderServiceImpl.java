package com.shixun.ihome.maintenance.service.serviceImpl;

import com.shixun.ihome.maintenance.service.OrderService;
import com.shixun.ihome.publicservice.mapper.IOrderMapper;
import com.shixun.ihome.publicservice.mapper.IOrderNewsMapper;
import com.shixun.ihome.publicservice.mapper.IToolrecordMapper;
import com.shixun.ihome.publicservice.pojo.IOrder;
import com.shixun.ihome.publicservice.pojo.IOrderNews;
import com.shixun.ihome.publicservice.pojo.IToolrecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private IOrderMapper orderMapper;
    @Autowired
    private IOrderNewsMapper orderNewsMapper;

    @Override
    public boolean addOrder(IOrder order) {
        orderMapper.insert(order);
        IOrderNews iOrderNews=new IOrderNews();
        iOrderNews.setOrderId(order.getId());
        orderNewsMapper.insert(iOrderNews);

        return true;
    }

    @Override
    public boolean cancelOrder(int id) {
        IOrder order=orderMapper.selectByPrimaryKey(id);
        System.out.println(order.getState());
        order.setState(1);
        orderMapper.updateByPrimaryKeySelective(order);
        return true;
    }

    @Override
    public boolean addDetail(int id, String o_describe, String solve) {
        IOrderNews orderNews=orderNewsMapper.selectByPrimaryKey(id);
        orderNews.setoDescribe(o_describe);
        orderNews.setSolve(solve);
        orderNewsMapper.updateByPrimaryKeySelective(orderNews);
        return true;
    }


}
