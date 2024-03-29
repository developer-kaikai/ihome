package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IStaff;
import com.shixun.ihome.publicservice.pojo.ITimer;
import com.shixun.ihome.publicservice.pojo.ITimerExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ITimerMapper {

    long MAXTIMER = 281474976710655l;

    int DAY = 63;

    int countByExample(ITimerExample example);

    int deleteByExample(ITimerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ITimer record);

    int insertSelective(ITimer record);

    List<ITimer> selectByExample(ITimerExample example);

    ITimer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ITimer record, @Param("example") ITimerExample example);

    int updateByExample(@Param("record") ITimer record, @Param("example") ITimerExample example);

    int updateByPrimaryKeySelective(ITimer record);

    int updateByPrimaryKey(ITimer record);

    //人工代码

    /**
     * 搜索空闲的员工
     * @param timer 时间表
     * @return  空闲员工列表
     */
    List<ITimer> selectFreeStaff(long timer);

    /**
     * 更新员工的时间表
     * @param params timer:时间表 int   staffId：员工Id int updateTime：更新时间
     * @return  更新数据的条目
     */
    int updateStaffTime(Map<String, Object> params);

    /**
     * 搜索空闲员工(钟点工)
     * @param map
     * @return
     */
    List<IStaff > selectStaffByFree(Map<String, Object> map);

    //获取空闲员工（其他）
    List<IStaff> selectStaffByFreeOther(Map<String,Object> map);
    //根据服务类型获取员工时间表(钟点工）
    List<ITimer> selectStaffTimer(Integer detailType);
    //根据服务类型获取员工时间表(其他员工）
    List<ITimer> selectOtherStaffTimer(Integer detailType);

}