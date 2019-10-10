package com.shixun.ihome.publicservice.mapper;

import com.shixun.ihome.publicservice.pojo.IStaff;
import com.shixun.ihome.publicservice.pojo.IStaffExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IStaffMapper {
    //休闲的
    int FREE = 0;
    //假期中
    int HOLIDAY = 1;
    //工作中
    int WORKING = 2;
    //无效的
    int IVALID = 3;


    int countByExample(IStaffExample example);

    int deleteByExample(IStaffExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IStaff record);

    int insertSelective(IStaff record);

    List<IStaff> selectByExample(IStaffExample example);

    IStaff selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IStaff record, @Param("example") IStaffExample example);

    int updateByExample(@Param("record") IStaff record, @Param("example") IStaffExample example);

    int updateByPrimaryKeySelective(IStaff record);

    int updateByPrimaryKey(IStaff record);

    /**
     * 根据服务类型搜索员工
     * @param servicetype_id 服务类型id
     * @return
     */
    List<IStaff> selectStaffByServicetypeId(int servicetype_id);

    /**
     * 根据条件搜索员工
     * @param istaff
     * @return
     */
    List<IStaff> selectStaffs(Map<String,Object> istaff);

    /*
    * 查询空闲长期工
    * */
    List<IStaff> selectLongTermStaffs();
}