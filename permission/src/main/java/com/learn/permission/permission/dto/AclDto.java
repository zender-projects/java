package com.learn.permission.permission.dto;

import com.learn.permission.permission.model.SysAcl;
import org.springframework.beans.BeanUtils;

public class AclDto extends SysAcl{

    private boolean checked = false;  //是否选中

    private boolean hasAcl = false; // 是否有权限操作

    public AclDto adapt(SysAcl acl){
        AclDto aclDto = new AclDto();
        BeanUtils.copyProperties(acl, aclDto);
        return aclDto;
    }


}
