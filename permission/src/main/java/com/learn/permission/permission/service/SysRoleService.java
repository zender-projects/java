package com.learn.permission.permission.service;

import com.learn.permission.permission.model.SysRole;
import com.learn.permission.permission.param.RoleParam;

import java.util.List;

public interface SysRoleService {

    void save(RoleParam roleParam);

    void update(RoleParam roleParam);

    List<SysRole> getAll();

    List<SysRole> getRoleListByUserId(Integer userId);

    List<SysRole> getRoleListByAclId(Integer aclid);
}
