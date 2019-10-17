package com.shixun.ihome.work.controller;


import com.github.pagehelper.PageInfo;
import com.shixun.ihome.config.ApiJsonObject;
import com.shixun.ihome.config.ApiJsonProperty;
import com.shixun.ihome.json.ResultBase;
import com.shixun.ihome.publicservice.pojo.IStaff;
import com.shixun.ihome.work.service.StaffService;
import com.shixun.ihome.work.service.TimeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "页", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "pageNum", value = "页数", paramType = "query", dataType = "int", required = true)
    })
    public ResultBase getAllStaffs(Integer pageNum, Integer pageSize){
        PageInfo<IStaff> pageInfo = staffService.selectStaffs(null, pageNum, pageSize);
        if(pageInfo.getSize() == 0){
            return ResultBase.fail("没有获取到员工数据");
        }
        return getPageData(pageInfo);
    }

    @ApiOperation("获取空闲的钟点工")
    @GetMapping("getFreeHourworkStaffs")
    @ApiImplicitParams({
            @ApiImplicitParam(name="timer", value = "时间表", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "pageNum", value = "页数", paramType = "query", dataType = "int", required = true)
    })
    public ResultBase getFreeHourworkStaffs(Integer timer, Integer pageNum, Integer pageSize){
        Map<String,Object> map = new HashMap<> ();
        map.put("timer", timer);
        map.put("status", 0);
        PageInfo<IStaff> pageInfo = staffService.selectHourworkStaffsByStatus(map);
        if (pageInfo.getSize() == 0){
            return ResultBase.fail("没有获取到空闲的钟点工");
        }
        return getPageData(pageInfo);
    }

    @ApiOperation(value="获取空闲长期工")
    @GetMapping("getFreeLongStaffs")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "页", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "pageNum", value = "页数", paramType = "query", dataType = "int", required = true)
    })
    public ResultBase getFreeLongStaffs(Integer pageNum, Integer pageSize){
        PageInfo<IStaff> pageInfo = staffService.selectStaffByServiceTypeAndStatus(4, 0, pageNum, pageSize);
        if (pageInfo.getSize()!= 0) {
            return ResultBase.fail("获取长期工数据失败");
        }
        return getPageData(pageInfo);
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
                    @ApiJsonProperty(key = "pageNum", example = "0", description = "页"),
                    @ApiJsonProperty(key = "pageSize", example = "10", description = "页数")
            })
            @RequestBody Map<String, Object> params){
        PageInfo<IStaff> pageInfo = staffService.selectHourworkStaffsByStatus(params);
        if (pageInfo.getSize()!= 0){
            return ResultBase.fail("获取钟点工失败");
        }
        return getPageData(pageInfo);
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
    @ApiImplicitParam(name="id", required = true, dataType ="int")
    @GetMapping("deleteStaff")
    @Transactional
    public ResultBase deleteStaff(Integer id) {
        IStaff record = staffService.getOne(id);
        if(staffService.deleteStaffRecord(record, "乔哥").equals("员工删除成功")){
            return new ResultBase(200, "删除员工成功");
        }
        return new ResultBase(400, "删除员工失败");
    }

    private ResultBase getPageData(PageInfo<IStaff> pageInfo){
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("pageNum", pageInfo.getPageNum());
        data.put("pageSize", pageInfo.getPageSize());
        data.put("total", pageInfo.getTotal());
        data.put("staffs", pageInfo.getList());
        return ResultBase.success(data);
    }

}
