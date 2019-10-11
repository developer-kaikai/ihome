package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IWeixin;
import com.shixun.ihome.publicservice.pojo.IWeixinExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWeixinMapper {
    int countByExample(IWeixinExample example);

    int deleteByExample(IWeixinExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IWeixin record);

    int insertSelective(IWeixin record);

    List<IWeixin> selectByExample(IWeixinExample example);

    IWeixin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IWeixin record, @Param("example") IWeixinExample example);

    int updateByExample(@Param("record") IWeixin record, @Param("example") IWeixinExample example);

    int updateByPrimaryKeySelective(IWeixin record);

    int updateByPrimaryKey(IWeixin record);

    IWeixin selectByopenid(String openid);
}