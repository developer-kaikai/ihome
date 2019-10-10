package com.shixun.ihome.backgroundsystem.controller;


import com.shixun.ihome.backgroundsystem.service.ComplaintService;
import com.shixun.ihome.json.Result;
import com.shixun.ihome.json.ResultType;
import com.shixun.ihome.publicservice.pojo.IOrderComplaint;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@Api(description = "订单投诉模块测试")
@RequestMapping("json/Complaint")
public class ComplaintController {
    @Autowired
    private ComplaintService complaintService;

    @ApiOperation(value = "增加订单投诉")
    @RequestMapping(value="/addComplaint",method = RequestMethod.POST)
    @ResponseBody
    public Boolean addCom(@RequestBody IOrderComplaint complaint){
        Boolean success=complaintService.addComplaint(complaint);
        return  success;
    }

    @ApiOperation(value = "订单投诉回复")
    @RequestMapping(value="/solveComplaint",method = RequestMethod.POST)
    @ResponseBody
    public Boolean solveCom(@RequestBody IOrderComplaint complaint){
        complaintService.solveCompaint(complaint);
        return  true;

    }

    @ApiOperation(value = "查看所有订单投诉")
    @RequestMapping(value="/allComplaint",method = RequestMethod.GET)
    @ResponseBody
    public void allCom(HttpServletResponse response)throws IOException {
        List<IOrderComplaint> complaints=complaintService.complaintlistAll();
        response.setContentType("application/json;charset=utf-8");
        String json ;
        json = Result.build(ResultType.Success).appendData("complaints", complaints).convertIntoJSON();

        response.getWriter().write(json);

    }


}
