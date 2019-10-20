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

    @ApiOperation(value="获取8天的时间表")
    @GetMapping("getTimers")
    public ResultBase getTimers(){
        RedisTimer redisTimer = redisTimerService.getTimer();
        return ResultBase.success(redisTimer);
    }

    @ApiOperation(value = "设置时间表")
    @PostMapping("setTimer")
    public ResultBase setTimer(@ApiJsonObject(name = "params", value = {
            @ApiJsonProperty(key = "timer", example = "000000", description = "时间表"),
            @ApiJsonProperty(key = "index", example = "0-7", description = "代表今天：0， 明天：1 以此类推")
    } )@RequestBody JSONObject params){
        Integer index = params.getInteger("index");
        String timer = params.getString("timer");
        redisTimerService.setTime(index, Integer.parseUnsignedInt(timer, 2));
        return ResultBase.success();
    }

    @ApiOperation(value = "设置所有时间表")
    @PostMapping("setTimerAll")
    public ResultBase setTimerAll(@ApiJsonObject(name = "params", value = {
            @ApiJsonProperty(key = "timers", example = "[0,0,0,0,0,0,0,0]", description = "8个int")
    }) @RequestBody JSONObject params){
        List<Integer> list = params.getJSONArray("timers").toJavaList(Integer.class);
        if (list.size() != 8){
            return ResultBase.fail("请检测数据是否正确");
        }
        for (int i = 0; i < 8; i++) {
            int timer = list.get(i);
            redisTimerService.setTime(i,timer );
        }
        return ResultBase.success();
    }


    @ApiOperation(value="动态生成可选日期和时间")
    @PostMapping("/getMessage")
    public ResultBase getMessage(@ApiJsonObject ( name = "name",value = {
            @ApiJsonProperty(key="hours", example = "2", description = "时间"),
            @ApiJsonProperty(key = "type", example = "0", description = "默认未0如果未1就返回只有上面时间")
    })@RequestBody JSONObject name){
        int hours=name.getInteger("hours");
        if (hours > 8 || hours <= 0){
            return ResultBase.fail("时间超出可以选择的范围");
        }
        //获取类型
        Integer type = name.getInteger("type");
        List<RedisTimerInfo> timerInfo = redisTimerService.getMessage(hours,type);
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
        PageInfo <IStaff> pageInfo = timeService.selectStaffByFree(map);
        Map<String, Object> data = new HashMap<>();
        data.put("data", pageInfo.getList());
        data.put("pageNum", pageInfo.getPageNum());
        data.put("pageSize", pageInfo.getPageSize());

        return ResultBase.success(data);
    }


}
