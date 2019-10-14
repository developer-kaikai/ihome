package com.shixun.ihome.work.controller;


import com.shixun.ihome.config.ApiJsonObject;
import com.shixun.ihome.config.ApiJsonProperty;
import com.shixun.ihome.json.ResultBase;
import com.shixun.ihome.publicservice.pojo.IStaff;
import com.shixun.ihome.publicservice.util.Qutil;
import com.shixun.ihome.work.service.StaffService;
import com.shixun.ihome.work.service.TimeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(description = "员工模块")
@RequestMapping("json/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;
    @Autowired
    private TimeService timerService;


    @ApiOperation(value = "添加员工")
    @ApiImplicitParam(name="iStaff", required = true, dataType = "IStaff" )
    @PostMapping("addStaff")
    @Transactional
    public ResultBase  addStaff(@RequestBody IStaff iStaff){
        //获取session中的工号
        staffService.addStaffRecord(iStaff, "乔哥");
        //为员工添加时间表
        timerService.addTimer(iStaff.getId());


        return new ResultBase(200, "添加员工成功");
    }

    @ApiOperation(value = "获取所有员工")
    @GetMapping("getAllStaffs")
    public ResultBase getAllStaffs(){
        List<IStaff> iStaffList = staffService.selectStaffs(null);
        ResultBase resultBase = new ResultBase();
        resultBase.setCode(200);
        resultBase.setData(iStaffList);
        return resultBase;
    }

    @ApiOperation("获取空闲的钟点工")
    @GetMapping("getFreeHourworkStaffs")
    @ApiImplicitParam(name="timer", value = "时间表", paramType = "query", dataType = "Integer", required = true)
    public ResultBase getFreeHourworkStaffs(Integer timer){
        Map<String,Object> map = new HashMap<String,Object> ();
        map.put("timer", timer);
        map.put("status", 0);
        List<IStaff> staffs = staffService.selectHourworkStaffsByStatus(map);
        if (staffs.isEmpty()){
            return new ResultBase(400,"没有获取空闲的钟点工");
        }
        return new ResultBase(200, staffs);
    }

    @ApiOperation(value="获取空闲长期工")
    @GetMapping("getFreeLongStaffs")
    public ResultBase getFreeLongStaffs(){
        List<IStaff> iStaffs = staffService.selectStaffByServiceTypeAndStatus(4, 0);
        if (iStaffs != null) {
            return new ResultBase(200, iStaffs);
        }
        return new ResultBase(400, "数据存在错误");
    }

    @ApiOperation(value="获取钟点工")

    @PostMapping("getHourwordStaffs")
    public ResultBase getHourwordStaffs(
            @ApiJsonObject(name = "params", value = {
                    @ApiJsonProperty(key = "timer", example = "0000000000000", description = "时间表"),
                    @ApiJsonProperty(key = "status", example = "0", description = "员工状态"),
                    @ApiJsonProperty(key = "sex", example = "0", description = "性别"),
                    @ApiJsonProperty(key = "name", example = "name", description = "姓名"),
                    @ApiJsonProperty(key = "phone", example = "131xxxx2365", description = "手机号"),
                    @ApiJsonProperty(key = "idCard", example = "441283597898225987", description = "身份证"),
            })
            @RequestBody Map<String, Object> params){
        List<IStaff> iStaffs = staffService.selectHourworkStaffsByStatus(params);
        if (iStaffs != null){
            return new ResultBase(200, iStaffs);
        }
        return new ResultBase(400, "获取数据失败");
    }

    @ApiOperation(value = "修改员工")
    @ApiImplicitParam(name="iStaff", required = true, dataType ="IStaff")
    @PostMapping("updateStaff")
    @Transactional
    public Object updateStaff(@RequestBody IStaff iStaff) {
        //获取旧记录
        IStaff iStaff1 = staffService.getOne(iStaff.getId());
        //获取session之中的修改人

        String bywho = "";
        //
        if (staffService.updateStaffRecord(iStaff1,iStaff,bywho)){
            return "修改成功";
        }
        return "修改失败";
    }

    @ApiOperation(value = "删除员工")
    @ApiImplicitParam(name="id", required = true, dataType ="Integer")
    @GetMapping("deleteStaff")
    @Transactional
    public ResultBase deleteStaff(Integer id) {
        IStaff record = staffService.getOne(id);
        if(staffService.deleteStaffRecord(record, "乔哥").equals("员工删除成功")){
            return new ResultBase(200, "删除员工成功");
        }
        return new ResultBase(400, "删除员工失败");
    }


}
