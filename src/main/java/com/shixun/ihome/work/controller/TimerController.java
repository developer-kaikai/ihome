package com.shixun.ihome.work.controller;

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
    @ApiImplicitParams({
            @ApiImplicitParam( name = "timer", value = "6个零一（从右到左）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam( name = "index", value = "0-7  意思是0今天 1明天 以此类推", required = true, paramType = "query", dataType = "int"),
    })
    @PostMapping("setTimer")
    public ResultBase setTimer(@RequestParam String timer, @RequestParam  Integer index){
        redisTimerService.setTime(index, Integer.parseUnsignedInt(timer, 2));
        return ResultBase.success();
    }


    @ApiOperation(value="动态生成可选日期和时间")
    @ApiImplicitParam( name = "hours", value = "2", required = true, paramType = "query", dataType= "int")
    @PostMapping("/getMessage")
    public ResultBase getMessage(Integer hours){
        if (hours > 8 || hours <= 0){
            return ResultBase.fail("时间超出可以选择的范围");
        }
        List<RedisTimerInfo> timerInfo = redisTimerService.getMessage(hours);
        return ResultBase.success(timerInfo);
    }


}
