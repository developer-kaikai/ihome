package com.shixun.ihome.work.controller;

import com.alibaba.fastjson.JSONObject;
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

@Controller
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
    @ApiOperation(value = "查询用户地址")
    @PostMapping("/selectUserAddress")
    @ResponseBody
    public void selectUserAddress(@ApiJsonObject(name = "iUserDetail", value = {
            @ApiJsonProperty(key = "id", example = "1", description = "用户ID")})
    @RequestBody JSONObject address, HttpServletResponse response) throws IOException {
        int id=address.getInteger("id");
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
    @ResponseBody
    public ResultBase addEvaluate(@ApiJsonObject(name = "iUserDetail", value = {
            @ApiJsonProperty(key = "userId", example = "1", description = "用户id"),
            @ApiJsonProperty(key = "userName", example = "蔡先生", description = "用户名称"),
            @ApiJsonProperty(key = "province", example = "广东", description = "省份"),
            @ApiJsonProperty(key = "city", example = "肇庆市", description = "城市"),
            @ApiJsonProperty(key = "detail", example = "端州区肇庆学院", description = "地址详情"),
            @ApiJsonProperty(key = "phone", example = "1352546897", description = "联系电话"),
            @ApiJsonProperty(key = "status", example = "0", description = "0为可用")
    })@RequestBody JSONObject addresss) {
        IUserDetail iUserDetail=new IUserDetail();
        int userId = addresss.getInteger("userId");
        String userName=addresss.getString("userName");
        String province=addresss.getString("province");
        String city=addresss.getString("city");
        String detail=addresss.getString("detail");
        String phone=addresss.getString("phone");
        int  status=addresss.getInteger("status");
        iUserDetail.setUserId(userId);
        iUserDetail.setUsername(userName);
        iUserDetail.setProvince(province);
        iUserDetail.setCity(city);
        iUserDetail.setDetail(detail);
        iUserDetail.setPhone(phone);
        iUserDetail.setStatus(status);
        boolean flag=userService.addUserDetail(iUserDetail);
        //System.out.println(flag);
        if (flag){

            return ResultBase.success();
        }
        return ResultBase.fail("添加失败");

    }
}
