package com.shixun.ihome.work.service;

import com.github.pagehelper.PageInfo;
import com.shixun.ihome.publicservice.pojo.IOrder;
import com.shixun.ihome.publicservice.pojo.ITimer;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TimeService {
    //插入时间表
    boolean addTimer(int staffId);

    //根据订单的开始日期和结束日期获取空闲员工
    List<ITimer> selectFreeStaff(Date startTimer, Date endTimer);



    String test(Date date1, Date date2);

    /**
     * 根据订单更新时间表
     * @param id
     * @param order
     * @return
     */
    boolean updateTimerByOrder(int id,  IOrder order);

    boolean removeTimerByOrder(int id, IOrder order);

    /**
     * 获取空闲员工(钟点工）
     * @param map index (今天到7天（0-7）） detailType 服务类型   status 状态
     * @return 员工
     */
    PageInfo selectStaffByFree(Map<String, Object> map);

    /**
     * 获取空闲员工（其他）
     * @param map index (今天到7天（0-7）） detailType 服务类型  status 状态
     * @return 员工列表
     */
    PageInfo selectStaffByFreeForOther(Map<String, Object> map);
}
