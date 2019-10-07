package com.shixun.ihome.backgroundsystem.service;

import com.shixun.ihome.publicservice.pojo.IOrderComplaint;

public interface ComplaintService {
    /*用户订单投诉*/
    Boolean addComplaint(IOrderComplaint complaint);

    /*投诉处理*/
    Boolean solveCompaint(IOrderComplaint complaint);

}
