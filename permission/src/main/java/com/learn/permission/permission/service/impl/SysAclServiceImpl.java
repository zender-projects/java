package com.learn.permission.permission.service.impl;

import com.learn.permission.common.bean.PageQuery;
import com.learn.permission.common.exception.ParameterException;
import com.learn.permission.common.result.PageDataResult;
import com.learn.permission.permission.dao.SysAclMapper;
import com.learn.permission.permission.model.SysAcl;
import com.learn.permission.permission.model.SysAclExample;
import com.learn.permission.permission.param.AclParam;
import com.learn.permission.permission.service.SysAclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class SysAclServiceImpl implements SysAclService{

    @Autowired
    private SysAclMapper mapper;

    @Override
    public void save(AclParam aclParam) {
        //重复检查
        if(checkExist(aclParam.getAclModuleId(), aclParam.getName(), null)){
            throw new ParameterException("权限资源已存在");
        }

        SysAcl acl = SysAcl.builder()
                        .name(aclParam.getName())
                        .aclModuleId(aclParam.getAclModuleId())
                        .url(aclParam.getUrl())
                        .type(aclParam.getType())
                        .status(aclParam.getStatus())
                        .seq(aclParam.getSeq())
                        .remark(aclParam.getRemark())
                        .build();
        acl.setCode(generateCode());
        //TODO RequestHolder
        acl.setOperator("admin");
        acl.setOperateIp("127.0.0.1");
        acl.setOperateTime(new Date());
        mapper.insertSelective(acl);
    }

    @Override
    public void update(AclParam aclParam) {

        //重复检查
        if(checkExist(aclParam.getAclModuleId(), aclParam.getName(), aclParam.getId())){
            throw new ParameterException("权限资源已存在");
        }

        SysAcl before = mapper.selectByPrimaryKey(aclParam.getId());
        if(Objects.isNull(before)){
            throw new ParameterException("待更新待权限点不存在");
        }

        SysAcl after = SysAcl.builder()
                .id(aclParam.getId())
                .name(aclParam.getName())
                .aclModuleId(aclParam.getAclModuleId())
                .url(aclParam.getUrl())
                .type(aclParam.getType())
                .status(aclParam.getStatus())
                .seq(aclParam.getSeq())
                .remark(aclParam.getRemark())
                .build();

        mapper.updateByPrimaryKeySelective(after);
    }

    @Override
    public PageDataResult<SysAcl> getPageByAclModuleId(Integer aclModuleId, PageQuery pageQuery) {

        SysAclExample example = new SysAclExample();
        SysAclExample.Criteria criteria = example.createCriteria();
        criteria.andAclModuleIdEqualTo(aclModuleId);
        long count = mapper.countByExample(example);
        if(count > 0) {
            List<SysAcl> aclList = mapper.selectPageByModuleId(aclModuleId, pageQuery.getOffset(), pageQuery.getPageSize());
            return PageDataResult.<SysAcl>builder().data(aclList).total((int)count).build();
        }

        return PageDataResult.<SysAcl>builder().build();
    }


    public Boolean checkExist(Integer aclModuleId, String aclName, Integer aclId){

        SysAclExample example = new SysAclExample();
        SysAclExample.Criteria criteria = example.createCriteria();
        criteria.andAclModuleIdEqualTo(aclModuleId);
        criteria.andNameEqualTo(aclName);

        if(!Objects.isNull(aclId)){
            criteria.andAclModuleIdNotEqualTo(aclId);
        }

        return mapper.countByExample(example) > 0;
    }

    public String generateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_" + (int)(Math.random() * 100);
    }
}
