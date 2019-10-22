package com.shixun.ihome.work.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.shixun.ihome.config.ApiJsonObject;
import com.shixun.ihome.config.ApiJsonProperty;
import com.shixun.ihome.json.ResultBase;
import com.shixun.ihome.publicservice.pojo.IStaff;
import com.shixun.ihome.publicservice.pojo.RedisTimer;
import com.shixun.ihome.publicservice.pojo.RedisTimerInfo;
import com.shixun.ihome.work.service.RedisTimerService;
import com.shixun.ihome.work.service.ServicetypeService;
import com.shixun.ihome.work.service.TimeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "时间表模块",description = "时间表相关")
@RequestMapping("/json/timer")
public class TimerController {
    @Autowired
    private RedisTimerService redisTimerService;
    @Autowired
    private TimeService timeService;
    @Autowired
    private ServicetypeService servicetypeService;







    @ApiOperation(value="动态生成可选日期和时间")
    @PostMapping("/getMessage")
    public ResultBase getMessage(@ApiJsonObject ( name = "name",value = {
            @ApiJsonProperty(key="hours", example = "2", description = "时间"),
            @ApiJsonProperty(key = "serviceId", example = "1"),
            @ApiJsonProperty(key = "type", example = "0", description = "默认未0如果未1就返回只有上面时间")
    })@RequestBody JSONObject name){
        int hours=name.getInteger("hours");
        int serviceId = name.getInteger("serviceId");
        Integer type = name.getInteger("type");
        if (type != null && type == 1){
            List<RedisTimerInfo> timerInfos = redisTimerService.getMessageOther(serviceId);
            return ResultBase.success(timerInfos);
        }
        if (hours > 8 || hours <= 0){
            return ResultBase.fail("时间超出可以选择的范围");
        }
        //获取类型
        List<RedisTimerInfo> timerInfo = redisTimerService.getMessage(serviceId,hours);
        return ResultBase.success(timerInfo);
    }

    @ApiOperation(value = "获取空闲员工")
    @PostMapping("/getFreeStaffs")
    public ResultBase getFreeStaffs(@ApiJsonObject(name = "map", value = {
            @ApiJsonProperty(key = "index", example = "0-7"),
            @ApiJsonProperty(key = "detailType", example = "详细服务类型id(可有可无)"),
            @ApiJsonProperty(key = "status", example = "员工状态(可有可无）"),
            @ApiJsonProperty(key = "pageSize", example = "10"),
            @ApiJsonProperty(key = "pageNum", example = "1")
    })@RequestBody Map<String, Object> map){
        Integer detailType = (Integer) map.get("detailType");
        PageInfo<IStaff> pageInfo = null;
        if (detailType == null){
            //详细服务id为空，默认不管服务类型全查
            pageInfo = timeService.selectStaffByFree(map);
        }else{
            //获取服务大类id
            Integer serviceId = servicetypeService.getServiceType(detailType);
            if (serviceId != 1){
                //如果存在服务大类id不为1就是不是钟点工，就搜索其他员工
                pageInfo = timeService.selectStaffByFreeForOther(map);
            }else{
                pageInfo = timeService.selectStaffByFree(map);
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("data", pageInfo.getList());
        data.put("pageNum", pageInfo.getPageNum());
        data.put("pageSize", pageInfo.getPageSize());

        return ResultBase.success(data);
    }




}
