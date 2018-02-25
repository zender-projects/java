package com.learn.permission.permission.service.impl;

import com.learn.permission.common.bean.RequestHolder;
import com.learn.permission.permission.dao.SysAclMapper;
import com.learn.permission.permission.dao.SysRoleAclMapper;
import com.learn.permission.permission.dao.SysRoleUserMapper;
import com.learn.permission.permission.model.*;
import com.learn.permission.permission.service.SysCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SysCoreServiceImpl implements SysCoreService{

    @Autowired
    private SysRoleAclMapper roleAclMapper;

    @Autowired
    private SysRoleUserMapper roleUserMapper;

    @Autowired
    private SysAclMapper aclMapper;

    @Override
    public List<SysAcl> getCurrentUserAclList() {
        Integer currentUserId = RequestHolder.getCurrentUser().getId();
        return getUserAclList(currentUserId);
    }

    @Override
    public List<SysAcl> getRoleAclList(Integer roleId) {
        //根据角色获取权限Id列表
        SysRoleAclExample roleAclExample = new SysRoleAclExample();
        SysRoleAclExample.Criteria criteria = roleAclExample.createCriteria();
        criteria.andRoleIdEqualTo(roleId);

        List<Integer> aclIdList = roleAclMapper.selectByExample(roleAclExample).stream().map(roleAcl -> roleAcl.getAclId()).collect(Collectors.toList());
        //根据权限Id列表获取权限列表


        SysAclExample aclExample = new SysAclExample();
        SysAclExample.Criteria criteria1 = aclExample.createCriteria();
        criteria1.andIdIn(aclIdList);

        return aclMapper.selectByExample(aclExample);
    }

    @Override
    public List<SysAcl> getUserAclList(Integer userId) {
        if(isSuperAdmin()){
            //返回所有权限
            return aclMapper.selectByExample(new SysAclExample());
        }
        //获取用户的角色Id列表
        SysRoleUserExample roleUserExample = new SysRoleUserExample();
        SysRoleUserExample.Criteria criteria = roleUserExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<Integer> userRoleIdList = roleUserMapper.selectByExample(roleUserExample).stream().map(roleUser -> roleUser.getRoleId()).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(userRoleIdList)) {
            return Collections.EMPTY_LIST;
        }
        //根据角色Id列表查询权Id限列表
        SysRoleAclExample roleAclExample = new SysRoleAclExample();
        SysRoleAclExample.Criteria criteria1 = roleAclExample.createCriteria();
        criteria1.andRoleIdIn(userRoleIdList);
        List<Integer> userAclIdList = roleAclMapper.selectByExample(roleAclExample).stream().map(roleAcl -> roleAcl.getAclId()).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(userAclIdList)) {
            return Collections.EMPTY_LIST;
        }
        //根据权限id查寻权限列表
        SysAclExample aclExample = new SysAclExample();
        SysAclExample.Criteria criteria2 = aclExample.createCriteria();
        criteria2.andIdIn(userAclIdList);
        return aclMapper.selectByExample(aclExample);
    }

    @Override
    public Boolean isSuperAdmin() {
        SysUser currentUser = RequestHolder.getCurrentUser();
        if(currentUser.getMail().contains("admin")){
            return true;
        }
        return false;
    }

    @Override
    public Boolean hasUrlAcl(String url) {
        return null;
    }

    @Override
    public List<SysAcl> getCurrentUserAclListFromCache() {
        return null;
    }
}
