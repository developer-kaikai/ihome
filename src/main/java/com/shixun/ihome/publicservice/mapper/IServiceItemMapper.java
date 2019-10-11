package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IServiceItem;
import com.shixun.ihome.publicservice.pojo.IServiceItemExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IServiceItemMapper {
    int countByExample(IServiceItemExample example);

    int deleteByExample(IServiceItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IServiceItem record);

    int insertSelective(IServiceItem record);

    List<IServiceItem> selectByExample(IServiceItemExample example);

    IServiceItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IServiceItem record, @Param("example") IServiceItemExample example);

    int updateByExample(@Param("record") IServiceItem record, @Param("example") IServiceItemExample example);

    int updateByPrimaryKeySelective(IServiceItem record);

    int updateByPrimaryKey(IServiceItem record);
}