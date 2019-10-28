package com.shixun.ihome.work.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.shixun.ihome.config.ApiJsonObject;
import com.shixun.ihome.config.ApiJsonProperty;
import com.shixun.ihome.json.Result;
import com.shixun.ihome.json.ResultBase;
import com.shixun.ihome.json.ResultType;
import com.shixun.ihome.publicservice.pojo.IUser;
import com.shixun.ihome.publicservice.pojo.IUserDetail;
import com.shixun.ihome.work.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@Api(description = "用户模块")
@RequestMapping("json/user")
public class UserController {
    @Autowired
    private UserService userService;


    @ApiOperation(value = "查询用户")
    @ApiImplicitParam(name = "user", value = "用户实体类", required = false, dataType = "IUser")
    @PostMapping("/selectUsers")
    public void selectUsers(@RequestBody IUser iUser,HttpServletResponse response) throws IOException {
        List<IUser> userList=userService.selectUsers(iUser);
        if (userList!= null) {
            response.setContentType("application/json;charset=utf-8");
            String json;
            json = Result.build(ResultType.Success).appendData("userList", userList).convertIntoJSON();
            response.getWriter().write(json);
        }

    }
    @ApiOperation(value = "查询用户默认地址")
    @PostMapping("/selectUserDefaultAddress")
    public void selectUserDefaultAddress(@ApiJsonObject(name = "iUserDetail", value = {
            @ApiJsonProperty(key = "userId", example = "1", description = "用户ID")})
    @RequestBody JSONObject address, HttpServletResponse response) throws IOException {
        int id=address.getInteger("userId");
        IUserDetail iUserDetail=userService.selectUserDefaultAddress(id);
        if (iUserDetail!= null) {
            response.setContentType("application/json;charset=utf-8");
            String json;
            json = Result.build(ResultType.Success).appendData("iUserDetail", iUserDetail).convertIntoJSON();
            response.getWriter().write(json);
        }

    }
    @ApiOperation(value = "查询用户地址")
    @PostMapping("/selectUserAddress")
    public void selectUserAddress(@ApiJsonObject(name = "iUserDetail", value = {
            @ApiJsonProperty(key = "userId", example = "1", description = "用户ID")})
                                  @RequestBody JSONObject address, HttpServletResponse response) throws IOException {
        int id=address.getInteger("userId");
        List<IUserDetail> addressList=userService.selectUserAddress(id);
        if (addressList!= null) {
            response.setContentType("application/json;charset=utf-8");
            String json;
            json = Result.build(ResultType.Success).appendData("addressList", addressList).convertIntoJSON();
            response.getWriter().write(json);
        }

    }
    @ApiOperation(value = "添加用户地址")
    @RequestMapping(value = "/addUserAddress", method = RequestMethod.POST)
    public ResultBase addUserAddress(@ApiJsonObject(name = "iUserDetail", value = {
            @ApiJsonProperty(key = "userId", example = "1", description = "用户id"),
            @ApiJsonProperty(key = "userName", example = "蔡先生", description = "用户名称"),
            @ApiJsonProperty(key = "province", example = "广东", description = "省份"),
            @ApiJsonProperty(key = "city", example = "肇庆市", description = "城市"),
            @ApiJsonProperty(key = "detail", example = "端州区肇庆学院", description = "地址详情"),
            @ApiJsonProperty(key = "phone", example = "1352546897", description = "联系电话"),
            @ApiJsonProperty(key = "status", example = "0", description = "0为可用")
    })@RequestBody JSONObject address) {
        IUserDetail iUserDetail=new IUserDetail();
        int userId = address.getInteger("userId");
        String userName=address.getString("userName");
        String province=address.getString("province");
        String city=address.getString("city");
        String detail=address.getString("detail");
        String phone=address.getString("phone");
        int  status=address.getInteger("status");
        iUserDetail.setUserId(userId);
        iUserDetail.setUsername(userName);
        iUserDetail.setProvince(province);
        iUserDetail.setCity(city);
        iUserDetail.setDetail(detail);
        iUserDetail.setPhone(phone);
        iUserDetail.setStatus(status);
        boolean flag=false;
        if(status==2){
            IUserDetail iUserDetail1=userService.selectUserDefaultAddress(userId);
            if(iUserDetail1==null){
                 flag=userService.addUserDetail(iUserDetail);
            }
            else{
                iUserDetail1.setStatus(0);
                flag=userService.updateUserDetail(iUserDetail1);
                flag=userService.addUserDetail(iUserDetail);
            }

        }else{
            flag=userService.addUserDetail(iUserDetail);
        }
        //boolean flag=userService.addUserDetail(iUserDetail);
        //System.out.println(flag);
        if (flag){

            return ResultBase.success();
        }
        return ResultBase.fail("添加失败");
    }
    @ApiOperation(value = "修改用户地址")
    @RequestMapping(value = "/updateUserAddress", method = RequestMethod.POST)
    public ResultBase updateUserAddress(@ApiJsonObject(name = "iUserDetail", value = {
            @ApiJsonProperty(key = "id", example = "1", description = "地址表id"),
            @ApiJsonProperty(key = "userId", example = "1", description = "用户id"),
            @ApiJsonProperty(key = "userName", example = "蔡先生", description = "用户名称"),
            @ApiJsonProperty(key = "province", example = "广东", description = "省份"),
            @ApiJsonProperty(key = "city", example = "肇庆市", description = "城市"),
            @ApiJsonProperty(key = "detail", example = "端州区肇庆学院", description = "地址详情"),
            @ApiJsonProperty(key = "phone", example = "1352546897", description = "联系电话"),
            @ApiJsonProperty(key = "status", example = "0", description = "0为可用")
    })@RequestBody JSONObject address) {
        IUserDetail iUserDetail=new IUserDetail();
        int id=address.getInteger("id");
        int userId = address.getInteger("userId");
        String userName=address.getString("userName");
        String province=address.getString("province");
        String city=address.getString("city");
        String detail=address.getString("detail");
        String phone=address.getString("phone");
        int  status=address.getInteger("status");
        iUserDetail.setId(id);
        iUserDetail.setUserId(userId);
        iUserDetail.setUsername(userName);
        iUserDetail.setProvince(province);
        iUserDetail.setCity(city);
        iUserDetail.setDetail(detail);
        iUserDetail.setPhone(phone);
        iUserDetail.setStatus(status);
        /*
        * 判断默认地址是否有改变
        * */
        boolean flag=false;
        if(status==2){
            IUserDetail iUserDetail1=userService.selectUserDefaultAddress(userId);
            int id1=iUserDetail1.getId();
            if(id==id1){
                 flag=userService.updateUserDetail(iUserDetail);
            }else {
                iUserDetail1.setStatus(0);
                flag=userService.updateUserDetail(iUserDetail1);
                flag=userService.updateUserDetail(iUserDetail);
            }
        }else{
            flag=userService.updateUserDetail(iUserDetail);
        }

        //System.out.println(flag);
        if (flag){

            return ResultBase.success();
        }
        return ResultBase.fail("修改失败");
    }
    @ApiOperation(value = "删除用户地址")
    @RequestMapping(value = "/deleteUserAddress", method = RequestMethod.POST)
    public ResultBase deleteUserAddress(@ApiJsonObject(name = "iUserDetail", value = {
            @ApiJsonProperty(key = "id", example = "1", description = "地址表id")
    })@RequestBody JSONObject address) {
        IUserDetail iUserDetail=new IUserDetail();
        int id=address.getInteger("id");

        iUserDetail.setId(id);
        iUserDetail.setStatus(1);

        boolean flag=false;
        flag = userService.updateUserDetail(iUserDetail);
        //System.out.println(flag);
        if (flag){

            return ResultBase.success();
        }
        return ResultBase.fail("删除失败");
    }

    @ApiOperation( value = "根据条件查询用户（用户表使用)")
    @PostMapping("/searchUserByCondition")
    public ResultBase searchUserByCondition(@ApiJsonObject(name = "params", value = {
            @ApiJsonProperty(key = "name", example = "姓名"),
            @ApiJsonProperty(key = "phone", example = "手机号"),
            @ApiJsonProperty(key = "pageNum", example = "1"),
            @ApiJsonProperty(key = "pageSize", example = "10"),
    })@RequestBody JSONObject params){
        IUser user = params.toJavaObject(IUser.class);
        Integer pageNum = params.getInteger("pageNum");
        Integer pageSize = params.getInteger("pageSize");
        PageInfo<IUser> lists = userService.selectUserByCondition(user, pageNum, pageSize);
        JSONArray arr = new JSONArray(lists.getSize());
        JSONObject data = new JSONObject();
        arr.addAll(lists.getList());
        JSONObject page = new JSONObject();
        page.put("pageSize", lists.getPageSize());
        page.put("pageNum", lists.getPageNum());
        page.put("total",lists.getTotal());
        data.put("page", page);
        data.put("users",arr);
        return ResultBase.success(data);
    }

    @ApiOperation(value = "获取用户的地址信息")
    @GetMapping("/getUserDetail/{id}")
    @ApiImplicitParam(name = "id", value = "1", required = true, paramType = "path", dataTypeClass = Integer.class)
    public ResultBase getUserDetail(@PathVariable Integer id){
        List<IUserDetail> lists  = userService.selectUserAddress(id);
        return ResultBase.success(lists);
    }
}
