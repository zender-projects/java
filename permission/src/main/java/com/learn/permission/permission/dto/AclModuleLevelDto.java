package com.learn.permission.permission.dto;

import com.learn.permission.permission.model.SysAclModule;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
public class AclModuleLevelDto extends SysAclModule{

     private List<AclModuleLevelDto> aclModuleList = new ArrayList<AclModuleLevelDto>();

     public static AclModuleLevelDto adapt(SysAclModule module){
         AclModuleLevelDto dto = new AclModuleLevelDto();
         BeanUtils.copyProperties(module, dto);
         return dto;
     }
}
