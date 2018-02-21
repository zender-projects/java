package com.learn.permission.permission.dao;

import com.learn.permission.permission.model.SysAclModule;
import com.learn.permission.permission.model.SysAclModuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysAclModuleMapper {
    long countByExample(SysAclModuleExample example);

    int deleteByExample(SysAclModuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    List<SysAclModule> selectByExample(SysAclModuleExample example);

    SysAclModule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysAclModule record, @Param("example") SysAclModuleExample example);

    int updateByExample(@Param("record") SysAclModule record, @Param("example") SysAclModuleExample example);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);
}