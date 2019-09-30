package com.shixun.ihome.hourwork.controller;

import com.shixun.ihome.hourwork.service.StaffService;
import com.shixun.ihome.hourwork.service.TimerService;
import com.shixun.ihome.publicservice.pojo.IStaff;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Api(description = "员工类操作控制器")
@RequestMapping("json/staff")
public class StaffController  {
    @Autowired
    private StaffService staffService;
    @Autowired
    private TimerService timerService;

    @ApiOperation(value = "寻找空闲的钟点工")
    @ApiImplicitParam(name="map",value ="订单实体类", required = true, dataType ="IOrder")
    @ResponseBody
    @RequestMapping("FindStaffs")
    public List<IStaff> FindStaffs(@RequestParam Map<String,String> map){

        IStaff iStaff = new IStaff();
        List<IStaff> iStaffList = staffService.selectStaffs(iStaff);
        return iStaffList;
    }


    public List<IStaff> FindFreeStaffs(@RequestParam Map<String, String> map) {
        return null;
    }
}
