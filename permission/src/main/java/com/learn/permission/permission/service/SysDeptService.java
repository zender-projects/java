package com.learn.permission.permission.service;

import com.learn.permission.permission.param.DeptParam;

public interface SysDeptService {

    Integer save(DeptParam deptParam);

    void update(DeptParam deptParam);

    void delete(Integer deptId);
}
