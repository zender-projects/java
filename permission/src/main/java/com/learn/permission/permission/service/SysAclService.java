package com.learn.permission.permission.service;


import com.learn.permission.common.bean.PageQuery;
import com.learn.permission.common.result.PageDataResult;
import com.learn.permission.permission.model.SysAcl;
import com.learn.permission.permission.param.AclParam;

public interface SysAclService {

    void save(AclParam aclParam);

    void update(AclParam aclParam);

    PageDataResult<SysAcl> getPageByAclModuleId(Integer aclModuleId, PageQuery pageQuery);

}
