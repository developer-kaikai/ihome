package com.shixun.ihome.backgroundsystem.controller;


import com.shixun.ihome.backgroundsystem.service.ComplaintService;
import com.shixun.ihome.publicservice.pojo.IOrderComplaint;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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


}
