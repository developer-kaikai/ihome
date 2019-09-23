package com.shixun.ihome.test.service;

import com.shixun.ihome.test.pojo.Users;

import java.util.List;

public interface UsersService {
    List<Users> selectAll();
    Users selectById(int id);
}
