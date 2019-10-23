package com.shixun.ihome.work.controller;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.pagehelper.PageInfo;
import com.shixun.ihome.config.ApiJsonObject;
import com.shixun.ihome.config.ApiJsonProperty;
import com.shixun.ihome.json.ResultBase;
import com.shixun.ihome.publicservice.pojo.IStaff;
import com.shixun.ihome.work.service.ServiceTimerService;
import com.shixun.ihome.work.service.ServicetypeService;
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
    @Autowired
    private ServiceTimerService serviceTimerService;
    @Autowired
    private ServicetypeService servicetypeService;


    @ApiOperation(value = "添加员工")
    @ApiImplicitParam(name="iStaff", required = true, dataType = "IStaff" )
    @PostMapping("addStaff")
    @Transactional
    public ResultBase  addStaff(@RequestBody IStaff iStaff){
        //获取session中的工号
        staffService.addStaffRecord(iStaff, "乔哥");
        //为员工添加时间表
        timerService.addTimer(iStaff.getId());
        //获取服务大类id
        int serviceId = servicetypeService.getServiceType(iStaff.getDetailtypeId());
        serviceTimerService.changeStaff(serviceId, 1);



        return new ResultBase(200, "添加员工成功");
    }

    @ApiOperation(value = "获取所有员工")
    @PostMapping("getAllStaffs")
    public ResultBase getAllStaffs(@ApiJsonObject(name = "params", value = {
            @ApiJsonProperty(key = "pageNum", example = "0", description = "页码"),
            @ApiJsonProperty(key = "pageSize", example = "10", description = "一页显示数量")
    })@RequestBody JSONObject params){
        int pageNum = params.getInteger("pageNum");
        int pageSize = params.getInteger("pageSize");
        PageInfo<IStaff> pageInfo = staffService.selectStaffs(null, pageNum, pageSize);
        if(pageInfo.getSize() == 0){
            return ResultBase.fail("没有获取到员工数据");
        }
        return getPageData(pageInfo);
    }


    @ApiOperation("获取空闲的钟点工")
    @PostMapping("getFreeHourworkStaffs")
    public ResultBase getFreeHourworkStaffs(@ApiJsonObject(name = "params", value = {
            @ApiJsonProperty(key = "pageNum", example = "0", description = "页码"),
            @ApiJsonProperty(key = "pageSize", example = "10", description = "一页显示数量")
    }) @RequestBody JSONObject params){
        Map<String,Object> map = new HashMap<> ();
        map.put("pageNum", params.getIntValue("pageNum"));
        map.put("pageSize", params.getIntValue("pageSize"));
        map.put("status", 0);
        PageInfo<IStaff> pageInfo = staffService.selectHourworkStaffsByStatus(map);
        if (pageInfo.getSize() == 0){
            return ResultBase.fail("没有获取到空闲的钟点工");
        }
        return getPageData(pageInfo);
    }


    @ApiOperation(value="获取空闲员工除了钟点工")
    @PostMapping("getFreeStaffs")
    public ResultBase getFreeStaffs(@ApiJsonObject(name = "params", value = {
            @ApiJsonProperty(key = "pageSize", example= "页"),
            @ApiJsonProperty(key = "pageNum", example= "页数"),
            @ApiJsonProperty(key = "type", example = "1")
    })@RequestBody JSONObject params){
        Integer pageNum = params.getInteger("pageNum");
        Integer pageSize = params.getInteger("pageSize");
        Integer type = params.getInteger("type");
        PageInfo<IStaff> pageInfo = staffService.selectStaffByServiceTypeAndStatus(type, 0, pageNum, pageSize);
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
        int detailId1  = iStaff1.getDetailtypeId();
        int detailId2 = iStaff.getDetailtypeId();

        String bywho = "";
        //
        if (staffService.updateStaffRecord(iStaff1,iStaff,bywho)){
            //如果管理员修改了员工的详细服务，就重新修改服务类时间表
            if (detailId1 != detailId2){
                int serviceId1 = servicetypeService.getServiceType(detailId1);
                int serviceId2 = servicetypeService.getServiceType(detailId2);
                serviceTimerService.changeStaff(serviceId1, -1);
                serviceTimerService.changeStaff(serviceId2, 1);
            }
            return "修改成功";
        }
        return "修改失败";
    }

    @ApiOperation(value = "删除员工")
    @PostMapping("deleteStaff")
    @Transactional
    public ResultBase deleteStaff(@ApiJsonObject(name = "params", value = {
            @ApiJsonProperty(key = "id", example = "1", description = "员工id")
    })@RequestBody JSONObject params) {
        IStaff record = staffService.getOne(params.getInteger("id"));
        if(staffService.deleteStaffRecord(record, "乔哥").equals("员工删除成功")){
            int serviceId = servicetypeService.getServiceType(record.getDetailtypeId());
            serviceTimerService.changeStaff(serviceId, -1);
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
