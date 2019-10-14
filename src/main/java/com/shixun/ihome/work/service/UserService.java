package com.shixun.ihome.work.service;

import com.shixun.ihome.publicservice.pojo.IUser;



import java.util.List;

public interface UserService {
    /*
    * 查询用户
    * */
    List<IUser> selectUsers(IUser iUser);


}
