package com.shixun.ihome.maintenance.service.serviceImpl;

import com.shixun.ihome.maintenance.service.OrderService;
import com.shixun.ihome.publicservice.mapper.IEvaluateMapper;
import com.shixun.ihome.publicservice.mapper.IOrderMapper;
import com.shixun.ihome.publicservice.mapper.IOrderNewsMapper;
import com.shixun.ihome.publicservice.mapper.IToolrecordMapper;
import com.shixun.ihome.publicservice.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private IOrderMapper orderMapper;
    @Autowired
    private IOrderNewsMapper orderNewsMapper;
    @Autowired
    private IEvaluateMapper evaluateMapper;

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
    public boolean addDetail(int id, String o_describe, String solve,Double price) {
       IOrder order=orderMapper.selectByPrimaryKey(id);

        IOrderNews orderNews=orderNewsMapper.selectByOrderID(id);
        System.out.println(orderNews.getId());
        orderNews.setoDescribe(o_describe);
        orderNews.setSolve(solve);
        orderNewsMapper.updateByPrimaryKeySelective(orderNews);

        order.setPrice(price);
        order.setFinalyTime(new Date());
        order.setState(4);
        orderMapper.updateByPrimaryKeySelective(order);

        return true;
    }

    @Override
    public boolean addEvaluate(int id, int quality_valuation, int attitude_valuation, String describe) {
        IEvaluate evaluate=new IEvaluate();
        evaluate.setOrderId(id);
        evaluate.setQualityValuation(quality_valuation);
        evaluate.setAttitudeValuation(attitude_valuation);
        evaluate.seteDescribe(describe);
        evaluateMapper.insert(evaluate);

        return true;
    }

    @Override
    public List<IOrder> listAll() {
        return orderMapper.listAllByName();
    }
}
