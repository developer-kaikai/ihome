package com.shixun.ihome.work.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shixun.ihome.publicservice.mapper.*;
import com.shixun.ihome.publicservice.pojo.*;
import com.shixun.ihome.publicservice.util.Qutil;
import com.shixun.ihome.work.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private IOrderMapper orderMapper;
    @Autowired
    private IOrderNewsMapper orderNewsMapper;
    @Autowired
    private IEvaluateMapper evaluateMapper;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    private IOrderLongMapper orderLongMapper;
    @Autowired
    private IOrderStaffMapper orderStaffMapper;


    @Override
    public List<IOrder> listbystaffidtwo(int staffid, int orderstate) {
        return orderMapper.listbystafftwo(staffid,orderstate);
    }

    @Override
    public Boolean updateOrderState(int orderid) {
        IOrder order=orderMapper.selectByPrimaryKey(orderid);
        order.setState(4);
        orderMapper.updateByPrimaryKeySelective(order);
        return  true;
    }

    @Override
    public List<IOrder> listbyuserTypename(int userid, String typename) {
        List<IDetailtype> listint=orderMapper.idbyTypename(typename);
        List<IOrder> maplist=new ArrayList<>();
        List<IOrder> orderList=new ArrayList<>();
        System.out.println(listint);
        for(int i=0;i<listint.size();i++){
            orderList=orderMapper.oderbyuserTypename(userid,listint.get(i).getId());
           maplist.addAll(orderList);
        }


        return maplist;
    }

    @Override
    public List<IOrder> listbystaffTypename(int staffid, String typename) {
        List<IDetailtype> listint=orderMapper.idbyTypename(typename);
        List<IOrder> maplist=new ArrayList<>();
        List<IOrder> orderList=new ArrayList<>();
        System.out.println(listint);
        for(int i=0;i<listint.size();i++){
            orderList=orderMapper.oderbystaffTypename(staffid,listint.get(i).getId());
            maplist.addAll(orderList);
        }


        return maplist;
    }

    @Override
    public List<IOrder> listbyuserid(int userid, int orderstate) {
        return orderMapper.listbyuser(userid,orderstate);
    }

    @Override
    public List<IOrder> listbystaffid(int staffid, int orderstate) {
        return orderMapper.listbystaff(staffid,orderstate);
    }

    @Override
    public boolean addOrderRecord(IOrder order, String byWho) {
        redisTemplate.delete("orderall");
        orderMapper.insert(order);
        IOrderNews iOrderNews=new IOrderNews();
        iOrderNews.setOrderId(order.getId());
        orderNewsMapper.insert(iOrderNews);
        List<IOrder> orderList=orderMapper.listAll();
        redisTemplate.opsForValue().set("orderall", orderList);

        return true;
    }

    @Override
    public boolean cancelOrder(int id) {

        IOrder order=orderMapper.selectByPrimaryKey(id);
        Date orderTimer = order.getOrderTime();
        if (!Qutil.assertTimer(new Date(), orderTimer, Qutil.MINUTE, 15)){
            order.setState(2);

            orderMapper.updateByPrimaryKeySelective(order);
            return false;
        }else{
            order.setState(1);

            orderMapper.updateByPrimaryKeySelective(order);
            return true;
        }
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
        evaluate.setFinallytime(new Date());
        evaluateMapper.insert(evaluate);

        return true;
    }

    @Override
    public List<IOrder> listAll() {
//        List<IOrder> orderList = (List<IOrder>) redisTemplate.opsForValue().get("orderall");
//        System.out.println("从Redis缓存中读出");
//       if(orderList==null){
        List<IOrder> orderList=orderMapper.listAll();
//           System.out.println("从数据库中读出");
//           redisTemplate.opsForValue().set("orderall", orderList);
//       }

        return orderList;
    }

    @Override
    public boolean deleteOrder(int id) {

        redisTemplate.delete("orderall");
        //创建order
        IOrder iOrder = new IOrder();
        iOrder.setId(id);
        iOrder.setState(IOrderMapper.IVALID);
        //修改作为删除
        orderMapper.updateByPrimaryKeySelective(iOrder);

        List<IOrder> orderList=orderMapper.listAll();
        redisTemplate.opsForValue().set("orderall", orderList);
        return true;
    }

    @Override
    public List<IOrder> selectOrder(IOrder order) {
        IOrderExample iOrderExample = new IOrderExample();
        IOrderExample.Criteria criteria = iOrderExample.createCriteria();
        if(order.getDetailtypeId() != null){
            criteria.andDetailtypeIdEqualTo(order.getDetailtypeId());
        }
        if(order.getUserId() != null){
            criteria.andUserIdEqualTo(order.getUserId());
        }
        if(order.getState() != null) {
            criteria.andStateEqualTo(order.getState());
        }
        return orderMapper.selectByExample(iOrderExample);
    }

    @Override
    public IOrder getOrder(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean addOrderLong(IOrderLong orderLong) {
        return orderLongMapper.insert(orderLong) > 0;
    }

    @Override
    public boolean addStaffForOrder(int orderId, int staffId) {
        //根据订单的预约时间，和工作时间更新员工的工作时间之类的。

        IOrderStaff orderStaff = new IOrderStaff();
        orderStaff.setOrderId(orderId);
        orderStaff.setStaffId(staffId);
        if (orderStaffMapper.insert(orderStaff) == 0){
            throw new RuntimeException("为订单添加员工失败");
        }
        return true;
    }

    @Override
    public boolean removeStaffForOrder(int orderId, int staffId) {
        IOrderStaffExample iOrderStaffExample = new IOrderStaffExample();
        IOrderStaffExample.Criteria criteria = iOrderStaffExample.createCriteria();
        criteria.andStaffIdEqualTo(staffId);
        criteria.andOrderIdEqualTo(orderId);

        if(orderStaffMapper.deleteByExample(iOrderStaffExample) == 0){
            throw new RuntimeException("确定订单是否分配了该员工");
        }
        return true;
    }

    @Override
    public List<IOrder> listByCondition(IOrder order) {
        List<IOrder> listh=(List<IOrder>) redisTemplate.opsForValue().get("orderbyCondition");
        System.out.println("从缓存读出");
        if (listh==null) {
            listh=orderMapper.listByCondition(order);
            System.out.println("从数据库读取");
            redisTemplate.opsForValue().set("orderall", listh);
        }
        return orderMapper.listByCondition(order);
    }

    @Override
    public PageInfo<IOrder> listByConditionPage(IOrder order, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<IOrder> pages = new PageInfo<>(orderMapper.listByCondition(order));
        return pages;
    }

    @Override
    public PageInfo<IOrder> selectByCondition(IOrder order, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        IOrderExample orderExample = new IOrderExample();
        IOrderExample.Criteria criteria = orderExample.createCriteria();
        if (order.getState() != null){
            criteria.andStateEqualTo(order.getState());
        }
        orderExample.setOrderByClause("id");
        PageInfo<IOrder> orders = new PageInfo<>(orderMapper.selectByExample(orderExample));
        return orders;
    }

    @Override
    public boolean updateOrderState(int orderId, int state) {
        IOrder order = new IOrder();
        order.setId(orderId);
        order.setState(state);
        return false;
    }

    @Override
    public List<IOrderStaff> selectOrderStaffs(int orderId) {
        return orderStaffMapper.selectStaffsByOrderId(orderId);
    }

    @Override
    public boolean addOrderLongDetail(IOrderLong orderLong, IOrderStaff orderStaff) {
        orderStaffMapper.insert(orderStaff);
        orderLongMapper.insert(orderLong);
        return true;
    }
}
