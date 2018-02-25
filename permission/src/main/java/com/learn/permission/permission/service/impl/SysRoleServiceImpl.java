package com.learn.permission.permission.service.impl;

import com.learn.permission.common.exception.ParameterException;
import com.learn.permission.permission.dao.SysRoleMapper;
import com.learn.permission.permission.dao.SysRoleUserMapper;
import com.learn.permission.permission.model.SysRole;
import com.learn.permission.permission.model.SysRoleExample;
import com.learn.permission.permission.model.SysRoleUserExample;
import com.learn.permission.permission.param.RoleParam;
import com.learn.permission.permission.service.SysRoleService;
import com.sun.xml.internal.rngom.digested.DDataPattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class SysRoleServiceImpl implements SysRoleService{


    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysRoleUserMapper roleUserMapper;

    @Override
    public void save(RoleParam roleParam) {
        //检查重复
        if(checkExist(roleParam.getName(), null)){
            throw new ParameterException("角色名称已存在");
        }
        SysRole role = SysRole.builder().name(roleParam.getName()).status(roleParam.getStatus()).type(roleParam.getType())
                .remark(roleParam.getRemark()).build();

        //TODO RequestHolder
        role.setOperator("admin");
        role.setOperateIp("127.0.0.1");
        role.setOperateTime(new Date());
        roleMapper.insertSelective(role);
    }

    @Override
    public void update(RoleParam roleParam) {

        if(checkExist(roleParam.getName(), roleParam.getId())){
            throw new ParameterException("角色名称已存在");
        }
        SysRole before = roleMapper.selectByPrimaryKey(roleParam.getId());
        if(Objects.isNull(before)){
            throw new ParameterException("待更新的角色不存在");
        }
        SysRole after = SysRole.builder().id(roleParam.getId()).name(roleParam.getName()).status(roleParam.getStatus()).type(roleParam.getType())
                .remark(roleParam.getRemark()).build();

        //TODO RequestHolder
        after.setOperator("admin");
        after.setOperateIp("127.0.0.1");
        after.setOperateTime(new Date());

    }

    @Override
    public List<SysRole> getAll() {
        return roleMapper.selectByExample(new SysRoleExample());
    }

    @Override
    public List<SysRole> getRoleListByUserId(Integer userId) {
        SysRoleUserExample example = new SysRoleUserExample();
        SysRoleUserExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);

        List<Integer> roleIds = new ArrayList<>();
        roleUserMapper.selectByExample(example).stream().forEach(role -> {
            roleIds.add(role.getId());
        });

        SysRoleExample roleExample = new SysRoleExample();
        SysRoleExample.Criteria roleCriteria = roleExample.createCriteria();
        roleCriteria.andIdIn(roleIds);

        return roleMapper.selectByExample(roleExample);
    }

    @Override
    public List<SysRole> getRoleListByAclId(Integer aclid) {
        return null;
    }

    public Boolean checkExist(String name, Integer id){
        SysRoleExample example = new SysRoleExample();
        SysRoleExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        if(!Objects.isNull(id)){
            criteria.andIdNotEqualTo(id);
        }
        return roleMapper.countByExample(example) > 0;
    }
}
