package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IDetailtype;
import com.shixun.ihome.publicservice.pojo.IOrder;
import com.shixun.ihome.publicservice.pojo.IOrderExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderMapper {
    //status
    //    状态\n0：已提交\n1：已取消\n2：待服务\n3：服务中\n4：已完成5:无效
    int SUBMIT = 0;
    int CANCEL = 1;
    int WAITSERVE = 2;
    int SERVEING = 3;
    int FINISH = 4;
    int IVALID = 5;

    int countByExample(IOrderExample example);

    int deleteByExample(IOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IOrder record);

    int insertSelective(IOrder record);

    List<IOrder> selectByExample(IOrderExample example);

    IOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IOrder record, @Param("example") IOrderExample example);

    int updateByExample(@Param("record") IOrder record, @Param("example") IOrderExample example);

    int updateByPrimaryKeySelective(IOrder record);

    int updateByPrimaryKey(IOrder record);

    List<IOrder> listByCondition(IOrder order);

    /*查询所有*/
    List<IOrder> listAll();

    /*用户/员工查看自己的订单*/
    List<IOrder> listbyuser(int userid,int orderstate);
    List<IOrder> listbystaff(int staffid,int orderstate);
    List<IOrder> listbystafftwo(int staffid,int orderstate);

    /*用户/员工查看自己的评价*/
    List<IOrder> listbyuserTypeid(int userid,int detailtypeid);
    List<IOrder> listbystaffTypeid(int staffid,int detailtype);

    List<IDetailtype> idbyTypename(String typename);

    /*用户/员工模糊找自己的订单*/
   List<IOrder> oderbyuserTypename(int userid,int id);
   List<IOrder> oderbystaffTypename(int staffid,int id);

   List<IOrder> listByLongTermOrder(IOrder record);


}