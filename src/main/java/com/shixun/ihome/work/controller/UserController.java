package com.shixun.ihome.work.controller;

import com.shixun.ihome.json.Result;
import com.shixun.ihome.json.ResultType;
import com.shixun.ihome.publicservice.pojo.IUser;
import com.shixun.ihome.work.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
