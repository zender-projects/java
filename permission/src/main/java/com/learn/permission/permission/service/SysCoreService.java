package com.learn.permission.permission.service;

import com.learn.permission.permission.model.SysAcl;

import java.util.List;

public interface SysCoreService {

    //获取当前用户点权限列表
    List<SysAcl> getCurrentUserAclList();


    //获取角色下点权限列表
    List<SysAcl> getRoleAclList(Integer roleId);


    //获取指定用户点权限列表
    List<SysAcl> getUserAclList(Integer userId);


    //判断当前用户是否是管理员
    Boolean isSuperAdmin();


    //是否有url权限
    Boolean hasUrlAcl(String url);


    //从缓存中获取当前用户点权限列表
    List<SysAcl> getCurrentUserAclListFromCache();

}
