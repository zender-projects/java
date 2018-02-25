package com.learn.permission.permission.service;

import com.learn.permission.permission.dto.AclModuleLevelDto;
import com.learn.permission.permission.dto.DeptLevelDto;

import java.util.List;

public interface SysTreeService {

    List<DeptLevelDto> deptTree();

    List<AclModuleLevelDto> aclModelTree();

    List<AclModuleLevelDto> roleTree();

}
