package com.shixun.ihome.work.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.shixun.ihome.config.ApiJsonObject;
import com.shixun.ihome.config.ApiJsonProperty;
import com.shixun.ihome.json.ResultBase;
import com.shixun.ihome.publicservice.pojo.IAdministration;
import com.shixun.ihome.work.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;


@RestController
@Api(description = "管理员模块")
@RequestMapping("json/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "添加管理员")
    @PostMapping("/addAdmin")
    public ResultBase addAdmin(@ApiJsonObject (name = "params", value = {
            @ApiJsonProperty(key = "idCard", example = "身份证"),
            @ApiJsonProperty(key = "name", example = "姓名"),
            @ApiJsonProperty(key = "phone", example = "手机号"),
            @ApiJsonProperty(key = "sex", example = "0"),
            @ApiJsonProperty(key = "age", example = "11"),
            @ApiJsonProperty(key = "address", example = "地址"),
            @ApiJsonProperty(key = "account", example = "账号"),
            @ApiJsonProperty(key = "pwd", example = "密码"),
            @ApiJsonProperty(key = "positionId", example = "1"),

    })@RequestBody JSONObject params, @ApiIgnore HttpSession session){
        IAdministration admin = params.toJavaObject(IAdministration.class);
        String bywho = "乔哥";
//        String bywho = (String) session.getAttribute("openId");
        if(adminService.addAdministrationRecord(admin,bywho)){
            return ResultBase.success();
        }
        return ResultBase.fail("添加员工失败");
    }


    @ApiOperation(value = "删除管理员账号")
    @GetMapping("/deleteAdmin/{id}")
    @ApiImplicitParam(name = "id", value = "1", paramType = "path", required = true, dataTypeClass = Integer.class)
    public ResultBase deleteAdmin(@PathVariable Integer id){
        IAdministration admin = adminService.getOne(id);
        if(adminService.deleteAdministrationRecord(admin, "乔哥")){
            return ResultBase.success();
        }
        return ResultBase.fail("删除管理员账号失败");
    }


    @ApiOperation(value = "修改管理员账号信息")
    @PostMapping("/updateAdmin/{id}")
    @ApiImplicitParam(name = "id", value = "1", paramType = "path", dataTypeClass = Integer.class, required = true)
    public ResultBase updateAdmin(@ApiJsonObject (name = "params", value = {
            @ApiJsonProperty(key = "idCard", example = "身份证"),
            @ApiJsonProperty(key = "name", example = "姓名"),
            @ApiJsonProperty(key = "phone", example = "手机号"),
            @ApiJsonProperty(key = "sex", example = "0"),
            @ApiJsonProperty(key = "age", example = "11"),
            @ApiJsonProperty(key = "address", example = "地址"),
            @ApiJsonProperty(key = "account", example = "账号"),
            @ApiJsonProperty(key = "pwd", example = "密码"),
            @ApiJsonProperty(key = "positionId", example = "1"),

    })@RequestBody JSONObject params,@PathVariable  Integer id,  @ApiIgnore HttpSession session){
        IAdministration newadmin= params.toJavaObject(IAdministration.class);
        IAdministration oldadmin = adminService.getOne(id);
        newadmin.setId(id);
        String bywho = "乔哥";
//        String bywho = (String) session.getAttribute("openId");
        if(adminService.updateAdministrationRecord(newadmin, oldadmin,bywho)){
            return ResultBase.success();
        }
        return ResultBase.fail("修改员工失败");
    }

    @ApiOperation(value = "根据条件搜索管理员信息")
    @PostMapping("/getAdmins")
    public ResultBase getAdmins(@ApiJsonObject(name = "params", value = {
            @ApiJsonProperty(key = "name", example = "姓名"),
            @ApiJsonProperty(key = "phone", example = "手机号"),
            @ApiJsonProperty(key = "positionId", example = "1"),
            @ApiJsonProperty(key = "pageSize", example = "10"),
            @ApiJsonProperty(key = "pageNum", example = "1")
    })@RequestBody JSONObject params){
        IAdministration admin = params.toJavaObject(IAdministration.class);
        Integer pageSize = params.getInteger("pageSize");
        Integer pageNum = params.getInteger("pageNum");
        PageInfo<IAdministration> pages = adminService.getAdmins(admin, pageSize, pageNum);
        JSONObject data=  new JSONObject();
        JSONObject page = new JSONObject();
        page.put("pageSize", pages.getPageSize());
        page.put("pageNum", pageNum);
        page.put("total",pages.getTotal());
        data.put("page", page);
        data.put("data",pages.getList());
        return ResultBase.success(data);
    }
}
