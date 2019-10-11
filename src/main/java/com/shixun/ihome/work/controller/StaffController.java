package com.shixun.ihome.work.controller;


import com.shixun.ihome.json.ResultBase;
import com.shixun.ihome.publicservice.pojo.IStaff;
import com.shixun.ihome.publicservice.util.Qutil;
import com.shixun.ihome.work.service.StaffService;
import com.shixun.ihome.work.service.TimeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@Controller
@Api(description = "员工模块")
@RequestMapping("json/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;
    @Autowired
    private TimeService timerService;


    @ApiOperation(value = "添加员工")
    @ApiImplicitParam(name="iStaff", required = true, dataType = "IStaff" )
    @ResponseBody
    @PostMapping("addStaff")
    public ResultBase  addStaff(@RequestBody IStaff iStaff, HttpSession session){
        //获取session中的工号
        String who = (String) session.getAttribute("id");
        IStaff iStaff1 =  staffService.addStaffRecord(iStaff, who);
        int staffId = iStaff1.getId();


        return new ResultBase(400, "添加员工失败");
    }

    @ApiOperation(value = "获取所有员工")
    @ResponseBody
    @GetMapping("getAllStaffs")
    public ResultBase getAllStaffs(){
        List<IStaff> iStaffList = staffService.selectStaffs(null);
        ResultBase resultBase = new ResultBase();
        resultBase.setCode(200);
        resultBase.setData(iStaffList);
        return resultBase;
    }

    public ResultBase getFreeHourworkStaffs(){

        return new ResultBase(400, "获取失败");
    }

    @ApiOperation(value="获取空闲长期工")
    @ResponseBody
    @GetMapping("getFreeLongStaffs")
    public ResultBase getFreeLongStaffs(){
        List<IStaff> iStaffs = staffService.selectStaffByServiceTypeAndStatus(4, 0);
        if (iStaffs != null) {
            return new ResultBase(200, iStaffs);
        }
        return new ResultBase(400, "数据存在错误");
    }

    @ApiOperation(value="获取钟点工")
    @ResponseBody

    @ApiImplicitParam(name = "params" , paramType = "body", value = "参数为空就是搜索全部\ntimer:14个01组成的字符串，代表7天上下午的工作状态1代表工作\nstatus:员工状态(0:休闲中、1：休假中、2：工作中、3：无效)\nsex：性别（0：男人、1：女人）\nname：姓名\nphone:手机号\nidCard:手机号")
    @PostMapping("getHourwordStaffs")
    public ResultBase getHourwordStaffs(@RequestBody Map<String, Object> params){
        if(params.get("timer") != null){
            String stimer = (String)params.get("timer");
            params.put("timer", Qutil.string2timer(stimer));
        }
        List<IStaff> iStaffs = staffService.selectHourworkStaffsByStatus(params);
        if (iStaffs != null){
            return new ResultBase(200, iStaffs);
        }
        return new ResultBase(400, "获取数据失败");
    }

    @ApiOperation(value = "修改员工")
    @ApiImplicitParam(name="iStaff", required = true, dataType ="IStaff")
    @ResponseBody
    @PostMapping("updateStaff")
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
    @ResponseBody
    @GetMapping("deleteStaff")
    public ResultBase deleteStaff(int id) {
        IStaff record = staffService.getOne(id);
        if(staffService.deleteStaffRecord(record, "乔哥").equals("员工删除成功")){
            return new ResultBase(200, "删除员工成功");
        }
        return new ResultBase(400, "删除员工失败");
    }


}
