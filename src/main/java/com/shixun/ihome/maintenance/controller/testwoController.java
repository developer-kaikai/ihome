package com.shixun.ihome.maintenance.controller;

import com.shixun.ihome.json.Result;
import com.shixun.ihome.json.ResultType;
import com.shixun.ihome.maintenance.service.testwoService;
import com.shixun.ihome.publicservice.pojo.IPosition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@Api(description = "维修模块测试")
@RequestMapping("json/test")
public class testwoController {
    @Autowired
    private testwoService testwoService1;

    @ApiOperation(value="列举职位")
    @GetMapping("/IPositionAll")
    @ResponseBody
    public void listAll(HttpServletResponse response)throws IOException {
        response.setContentType("application/json;charset=utf-8");

        List<IPosition> listp=testwoService1.iPositionListAll();
        String json ;
        json = Result.build(ResultType.Success).appendData("listp", listp).convertIntoJSON();

        response.getWriter().write(json);


    }
}
