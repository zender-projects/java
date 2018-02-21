package com.learn.permission.permission.service;

import com.learn.permission.common.bean.PageQuery;
import com.learn.permission.common.result.PageDataResult;
import com.learn.permission.permission.model.SysUser;
import com.learn.permission.permission.param.UserParam;

public interface SysUserService {

    void save(UserParam userParam);

    void update(UserParam userParam);

    void delete(Integer id);

    SysUser queryByKeyword(String keyword);

    PageDataResult<SysUser> getPageByDeptId(Integer deptId, PageQuery pageQuery);
}
