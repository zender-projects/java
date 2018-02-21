package com.learn.permission.permission.service;

import com.learn.permission.permission.model.SysUser;
import com.learn.permission.permission.param.UserParam;

public interface SysUserService {

    void save(UserParam userParam);

    void update(UserParam userParam);

    SysUser queryByKeyword(String keyword);
}
