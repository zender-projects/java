package com.learn.permission.permission.service.impl;

import com.learn.permission.common.exception.ParameterException;
import com.learn.permission.common.util.MD5Util;
import com.learn.permission.common.util.PasswordUtil;
import com.learn.permission.permission.dao.SysUserMapper;
import com.learn.permission.permission.model.SysUser;
import com.learn.permission.permission.model.SysUserExample;
import com.learn.permission.permission.param.UserParam;
import com.learn.permission.permission.service.SysUserService;
import com.sun.org.glassfish.gmbal.ManagedObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService{

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public void save(UserParam userParam) {

        if(checkEmail(userParam.getTelephone(), null)){
            throw new ParameterException("邮箱["+ userParam.getMail()+"]已被占用");
        }
        if(checkTelephone(userParam.getTelephone(), null)) {
            throw new ParameterException("手机号[" + userParam.getTelephone() +"]已被占用");
        }
        String password = PasswordUtil.randomPassword();
        //密码加密
        String encPassword = MD5Util.encrypt(password);
        SysUser newUser = SysUser.builder().username(userParam.getUsername())
                .telephone(userParam.getTelephone())
                .mail(userParam.getMail())
                .password(password)
                .deptId(userParam.getDeptId())
                .status(userParam.getStatus())
                .remark(userParam.getRemark()).build();

        newUser.setOperator("system");
        newUser.setOperateIp("localhost");
        newUser.setOperateTime(new Date());

        //TODO : send email
        userMapper.insertSelective(newUser);

    }

    @Override
    public void update(UserParam userParam) {

        //获取修改之前的数据
        SysUser oldUser = userMapper.selectByPrimaryKey(userParam.getId());
        if(Objects.isNull(oldUser)) {
            throw new ParameterException("待更新待用户不存在");
        }

        if(!oldUser.getMail().equals(userParam.getMail())) {
           if(checkEmail(userParam.getMail(), userParam.getId())) {
               throw new ParameterException("邮箱["+ userParam.getMail()+"]已被占用");
           }
        }

        if(!oldUser.getTelephone().equals(userParam.getTelephone())) {
            if(checkTelephone(userParam.getTelephone(), userParam.getId())){
                throw new ParameterException("手机号[" + userParam.getTelephone() +"]已被占用");
            }
        }

        SysUser newUser = SysUser.builder().id(userParam.getId())
                .username(userParam.getUsername())
                .telephone(userParam.getTelephone())
                .mail(userParam.getMail())
                .deptId(userParam.getDeptId())
                .status(userParam.getStatus())
                .remark(userParam.getRemark()).build();

        userMapper.updateByPrimaryKeySelective(newUser);
    }

    @Override
    public SysUser queryByKeyword(String keyword) {
        if(Objects.isNull(keyword)) {
            throw new ParameterException("关键字为空");
        }
        return userMapper.selectByKeyword(keyword);
    }

    //检查邮箱是否以及存在
    private boolean checkEmail(String email, Integer userId) {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        criteria.andMailEqualTo(email);
        if(!Objects.isNull(userId)){
            criteria.andIdNotEqualTo(userId);
        }

        return userMapper.selectByExample(example).size() > 0;
    }

    //检查电话号码是否以及存在
    private boolean checkTelephone(String telephone,Integer userId) {

        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        criteria.andTelephoneEqualTo(telephone);
        if(!Objects.isNull(userId)){
            criteria.andIdNotEqualTo(userId);
        }

        return userMapper.selectByExample(example).size() > 0;
    }
}
