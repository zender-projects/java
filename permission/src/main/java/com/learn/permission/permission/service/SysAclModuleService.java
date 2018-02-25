package com.learn.permission.permission.service;

import com.learn.permission.permission.param.AclModuleParam;

public interface SysAclModuleService {

    void save(AclModuleParam aclModuleParam);

    void update(AclModuleParam aclModuleParam);

    void delete(Integer aclModuleId);
}
