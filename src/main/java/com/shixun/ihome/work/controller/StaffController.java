package com.shixun.ihome.work.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.pagehelper.PageInfo;
import com.shixun.ihome.config.ApiJsonObject;
import com.shixun.ihome.config.ApiJsonProperty;
import com.shixun.ihome.json.ResultBase;
import com.shixun.ihome.publicservice.pojo.IStaff;
import com.shixun.ihome.publicservice.util.Qutil;
import com.shixun.ihome.work.service.ServiceTimerService;
import com.shixun.ihome.work.service.ServicetypeService;
import com.shixun.ihome.work.service.StaffService;
import com.shixun.ihome.work.service.TimeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
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
        iStaff.setHealth("");
        iStaff.setIdCard("");
        iStaff.setQualification("");
        staffService.addStaffRecord(iStaff, "乔哥");
        //为员工添加时间表
        timerService.addTimer(iStaff.getId());
        //获取服务大类id
        int serviceId = servicetypeService.getServiceType(iStaff.getDetailtypeId());
        serviceTimerService.changeStaff(serviceId, 1);
        return ResultBase.success();
    }

    @ApiOperation(value = "员工高级搜索")
    @PostMapping("/heightSearch")
    public ResultBase heightSearch(@ApiJsonObject(name = "params", value = {
            @ApiJsonProperty(key = "name", example = "乔", description = "员工姓名"),
            @ApiJsonProperty(key = "sex", example = "1", description = "0：男 1：女"),
            @ApiJsonProperty(key = "phoen", example = "111111111111", description = "11位手机号"),
            @ApiJsonProperty(key = "detailtypeId",example = "1", description = "详细服务类"),
            @ApiJsonProperty(key = "status", example = "0", description = "0：休闲中 1：休假中 2：服务中 3:无效"),
            @ApiJsonProperty(key = "pageSize", example = "10", description = "最大显示页数"),
            @ApiJsonProperty(key = "pageNum", example = "1", description = "当前的页数")
    })@RequestBody JSONObject params){
        IStaff staff = params.toJavaObject(IStaff.class);
        Integer pageSize = params.getInteger("pageSize");
        Integer pageNum  = params.getInteger("pageNum");
        PageInfo<IStaff> pages = staffService.selectStaffs(staff, pageNum, pageSize);
        JSONObject data = new JSONObject();
        data.put("total",pages.getTotal());
        data.put("pageSize",pages.getPageSize());
        data.put("pageNum",pageNum);
        JSONArray arr = new JSONArray(pages.getSize());
        arr.addAll(pages.getList());
        data.put("list", arr);
        return ResultBase.success(data);
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


    @ApiOperation("获取空闲的员工")
    @PostMapping("getFreeHourworkStaffs")
    public ResultBase getFreeHourworkStaffs(@ApiJsonObject(name = "params", value = {
            @ApiJsonProperty(key = "pageNum", example = "0", description = "页码"),
            @ApiJsonProperty(key = "pageSize", example = "10", description = "一页显示数量"),
            @ApiJsonProperty(key = "serviceId", example = "1", description = "1为钟点工，其他未其他员工")
    }) @RequestBody JSONObject params){
        Map<String,Object> map = new HashMap<> ();
        map.put("pageNum", params.getIntValue("pageNum"));
        map.put("pageSize", params.getIntValue("pageSize"));
        PageInfo<IStaff> pageInfo = staffService.selectHourworkStaffsByStatus(map);
        if (pageInfo.getSize() == 0){
            return ResultBase.fail("没有获取到空闲的钟点工");
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

    @ApiOperation(value = "恢复员工")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "1", required = true, paramType = "path", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "detailtypeId", value = "1", required = true, paramType = "path", dataTypeClass = Integer.class)
    })
    @Transactional
    @GetMapping("/recoverStaff/{detailtypeId}/{id}")
    public ResultBase recoverStaff(@PathVariable Integer id, @PathVariable Integer detailtypeId){
        if(staffService.updateStaffStatus(id, 0, 3)){
            Integer serviceID = servicetypeService.getServiceType(detailtypeId);
            serviceTimerService.changeStaff(serviceID, 1);
            return ResultBase.success();
        }
        return ResultBase.fail();
    }
    @ApiOperation(value = "修改员工")
    @PostMapping("updateStaff")
    @Transactional
    public ResultBase updateStaff(@ApiJsonObject(name = "params", value = {
            @ApiJsonProperty(key = "id", example = "1"),
            @ApiJsonProperty(key = "sex", example = "1"),
            @ApiJsonProperty(key = "phone", example = "手机号"),
            @ApiJsonProperty(key = "detailtypeId", example = "1"),
            @ApiJsonProperty(key = "status", example = "状态")
    })@RequestBody JSONObject params) {
        //读取数据
        IStaff iStaff2 =params.toJavaObject(IStaff.class);
        System.out.println(iStaff2);
        IStaff iStaff = new IStaff();
        Integer id =params.getInteger("id");
        Integer sex =params.getInteger("sex");
        String phone =params.getString("phone");
        Integer detailtypeId =params.getInteger("detailtypeId");
        iStaff.setId(id);
        iStaff.setSex(sex);
        iStaff.setPhone(phone);
        iStaff.setDetailtypeId(detailtypeId);


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
            return ResultBase.success(iStaff1);
        }
        return ResultBase.fail();
    }

    @ApiOperation(value = "获取单个员工数据")
    @GetMapping("/getOne")
    @ApiImplicitParam(name = "id", value = "1", paramType = "path", dataTypeClass = Integer.class, required = true)
    public ResultBase getOne(@PathVariable Integer id){
        IStaff staff = staffService.getOne(id);
        return ResultBase.success(staff);
    }


    @ApiOperation(value = "文件上传（包括身份证、从业资格证、健康证）")
    @PostMapping("/uploadFile/{staffId}/{type}")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "staffId", value = "1", paramType = "path", dataTypeClass = Integer.class, required = true),
            @ApiImplicitParam(name = "type", value = "0",example = "0:身份证，1：从业资格证、2：健康证", paramType = "path", dataTypeClass = Integer.class, required = true)
    })
    public ResultBase uploadFile(@RequestParam MultipartFile file, @PathVariable Integer staffId, @PathVariable Integer type){
        //获取文件
        if(file == null){
            return ResultBase.fail("没有接收到文件");
        }
        //获取旧记录
        IStaff oldStaff = staffService.getOne(staffId );
        //构建新记录
        IStaff staff = new IStaff();
        staff.setId(staffId);
        String filename = file.getOriginalFilename();
        Date date = new Date();
        String filepath =  date.getTime() + filename;
        String path = "";
        switch(type){
            case 0: {
                //判断旧的记录是否存在文件
                path = "C:/Files/IdCard/" + filepath;
                staff.setIdCard("/image/IdCard/" + filepath);
            }break;
            case 1:{
                path = "C:/Files/Qualification/" +filepath;
                staff.setQualification("/image/Qualification/" + filepath);
            }break;
            case 2:{
                path = "C:/Files/Health/" + filepath;
                staff.setHealth("/image/Health/" + filepath);
            }break;
        }
        Qutil.deleteFile(path);
        File dest = new File(path );
        try{
            file.transferTo(dest);
            //更新数据库
            staffService.updateStaffFile(staff);
            return ResultBase.success();
        }catch (IOException e){
            e.printStackTrace();
            return ResultBase.fail("文件上传发生错误");
        }
    }


    @ApiOperation(value = "删除员工")
    @PostMapping("/deleteStaff")
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
