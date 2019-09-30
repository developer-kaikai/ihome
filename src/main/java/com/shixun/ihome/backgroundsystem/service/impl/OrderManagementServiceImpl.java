package com.shixun.ihome.backgroundsystem.service.impl;

import com.shixun.ihome.backgroundsystem.service.OrderManagementService;
import com.shixun.ihome.publicservice.mapper.IEvaluateMapper;
import com.shixun.ihome.publicservice.mapper.IOrderMapper;
import com.shixun.ihome.publicservice.mapper.IOrderNewsMapper;
import com.shixun.ihome.publicservice.pojo.IOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderManagementServiceImpl implements OrderManagementService {
    @Autowired
    private IOrderMapper orderMapper;
    @Autowired
    private IOrderNewsMapper orderNewsMapper;
    @Autowired
    private IEvaluateMapper evaluateMapper;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;


    @Override
    public List<IOrder> listAll() {
        List<IOrder> orderList = (List<IOrder>) redisTemplate.opsForValue().get("orderall");
        System.out.println("从Redis缓存中读出");
        if(orderList==null){
            orderList=orderMapper.listAll();
            System.out.println("从数据库中读出");
            redisTemplate.opsForValue().set("orderall", orderList);
        }

        return orderList;
    }

    @Override
    public boolean deleteOrder(int id) {
        IOrder order=orderMapper.selectByPrimaryKey(id);
        order.setState(5);
        orderMapper.updateByPrimaryKeySelective(order);
        //更新缓存
        List<IOrder> orderList=orderMapper.listAll();
        redisTemplate.opsForValue().set("orderall", orderList);

        return true;
    }

    @Override
    public List<IOrder> listByCondition(IOrder order) {
//        List<IOrder> listh=(List<IOrder>) redisTemplate.opsForValue().get("orderbyCondition");
//        System.out.println("从缓存读出");
//        if (listh==null) {
//            listh=orderMapper.listByCondition(order);
//            System.out.println("从数据库读取");
//            redisTemplate.opsForValue().set("orderall", listh);
//        }
        return orderMapper.listByCondition(order);
    }
}
