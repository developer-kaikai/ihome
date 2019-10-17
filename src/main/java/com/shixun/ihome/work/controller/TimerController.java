package com.shixun.ihome.work.controller;

import com.alibaba.fastjson.JSONObject;
import com.shixun.ihome.config.ApiJsonObject;
import com.shixun.ihome.config.ApiJsonProperty;
import com.shixun.ihome.json.ResultBase;
import com.shixun.ihome.publicservice.pojo.RedisTimer;
import com.shixun.ihome.publicservice.pojo.RedisTimerInfo;
import com.shixun.ihome.work.service.RedisTimerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "时间表模块",description = "时间表相关")
@RequestMapping("/json/timer")
public class TimerController {
    @Autowired
    private RedisTimerService redisTimerService;

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


    @ApiOperation(value="动态生成可选日期和时间")
    @PostMapping("/getMessage")
    public ResultBase getMessage(@ApiJsonObject ( name = "name",value = {
            @ApiJsonProperty(key="hours", example = "2", description = "时间")
    })@RequestBody JSONObject name){
        int hours=name.getInteger("hours");
        if (hours > 8 || hours <= 0){
            return ResultBase.fail("时间超出可以选择的范围");
        }
        List<RedisTimerInfo> timerInfo = redisTimerService.getMessage(hours);
        return ResultBase.success(timerInfo);
    }


}
