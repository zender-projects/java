package com.learn.permission.permission.dao;

import com.learn.permission.permission.model.SysAcl;
import com.learn.permission.permission.model.SysAclExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

public interface SysAclMapper {
    long countByExample(SysAclExample example);

    int deleteByExample(SysAclExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysAcl record);

    int insertSelective(SysAcl record);

    List<SysAcl> selectByExample(SysAclExample example);

    SysAcl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysAcl record, @Param("example") SysAclExample example);

    int updateByExample(@Param("record") SysAcl record, @Param("example") SysAclExample example);

    int updateByPrimaryKeySelective(SysAcl record);

    int updateByPrimaryKey(SysAcl record);

    @Select("select * from sys_acl where acl_module_id = #{moduleId} limit #{offset}, #{limit}")
    List<SysAcl> selectPageByModuleId(@Param("moduleId") Integer moduleId,
                                      @Param("offset") Integer offset,
                                      @Param("limit") Integer limit);


}