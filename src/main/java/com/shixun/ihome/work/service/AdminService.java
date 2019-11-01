package com.shixun.ihome.work.service;

import com.github.pagehelper.PageInfo;
import com.shixun.ihome.publicservice.pojo.IAdministration;

public interface AdminService {

    //登录密码验证
    int selectPwd(String account,String pwd);
    //获取单个数据
    IAdministration getOne(int id);
    //添加管理员
    boolean addAdministrationRecord(IAdministration admin, String bywho);
    //修改管理员
    boolean updateAdministrationRecord(IAdministration newAdmin, IAdministration oldAdmin, String bywho);
    //根据条件查询管理员
    PageInfo<IAdministration> getAdmins(IAdministration admin, int pageSize, int pageNum);
    //删除管理员
    boolean deleteAdministrationRecord(IAdministration admin, String byWho);

}
