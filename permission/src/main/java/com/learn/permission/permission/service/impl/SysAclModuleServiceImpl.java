package com.learn.permission.permission.service.impl;

import com.learn.permission.common.exception.ParameterException;
import com.learn.permission.common.util.LevelUtil;
import com.learn.permission.permission.dao.SysAclMapper;
import com.learn.permission.permission.dao.SysAclModuleMapper;
import com.learn.permission.permission.model.SysAclExample;
import com.learn.permission.permission.model.SysAclModule;
import com.learn.permission.permission.model.SysAclModuleExample;
import com.learn.permission.permission.param.AclModuleParam;
import com.learn.permission.permission.service.SysAclModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class SysAclModuleServiceImpl implements SysAclModuleService{

    @Autowired
    private SysAclModuleMapper moduleMapper;

    @Autowired
    private SysAclMapper aclMapper;


    @Override
    public void save(AclModuleParam aclModuleParam) {
        //检查新增模块是否已经存在
        if(checkExist(aclModuleParam.getName(), aclModuleParam.getParentId(), null)){
            throw new ParameterException("该模块已存在");
        }

        //构造AclModule
        SysAclModule aclModule = SysAclModule.builder()
                                    .name(aclModuleParam.getName())
                                    .parentId(aclModuleParam.getParentId())
                                    .seq(aclModuleParam.getSeq())
                                    .status(aclModuleParam.getStatus())
                                    .remark(aclModuleParam.getRemark()).build();

        //计算level
        String level = LevelUtil.calculateLevel(getLevel(aclModuleParam.getParentId()), aclModuleParam.getParentId());
        aclModule.setLevel(level);

        //TODO LoginFilter -> RequestHolder
        aclModule.setOperator("admin");
        aclModule.setOperateIp("127.0.0.1");
        aclModule.setOperateTime(new Date());

        //保存数据
        moduleMapper.insertSelective(aclModule);

    }

    @Override
    public void update(AclModuleParam aclModuleParam) {

        //根据id查询修改前等模块数据
        SysAclModule aclModuleOld = moduleMapper.selectByPrimaryKey(aclModuleParam.getId());
        //如果模块名称发生变化，则检查模块名称是否存在
        if(!aclModuleOld.getName().equals(aclModuleOld.getName())){
            if(checkExist(aclModuleParam.getName(), aclModuleParam.getParentId(), aclModuleParam.getId())){
                throw new ParameterException("该模块已存在");
            }
        }
        //如果没有变化，或变化后的名称不存在，则直接修改
        //构造AclModule
        SysAclModule aclModuleNew = SysAclModule.builder()
                .id(aclModuleParam.getId())
                .name(aclModuleParam.getName())
                .parentId(aclModuleParam.getParentId())
                .seq(aclModuleParam.getSeq())
                .status(aclModuleParam.getStatus())
                .remark(aclModuleParam.getRemark()).build();
        //计算level
        String level = LevelUtil.calculateLevel(getLevel(aclModuleParam.getParentId()), aclModuleParam.getParentId());
        aclModuleNew.setLevel(level);

        //TODO LoginFilter -> RequestHolder
        aclModuleNew.setOperator("admin");
        aclModuleNew.setOperateIp("127.0.0.1");
        aclModuleNew.setOperateTime(new Date());

        //根据本模块及所有子模块

    }

    @Transactional
    private void updateWithChild(SysAclModule before, SysAclModule after){
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();

        //如果level发生变化,则更新子模块
        if(!newLevelPrefix.equals(oldLevelPrefix)){
            SysAclModuleExample example = new SysAclModuleExample();
            SysAclModuleExample.Criteria criteria = example.createCriteria();
            criteria.andLevelLike(oldLevelPrefix + ".%");
            List<SysAclModule> childModuleList = moduleMapper.selectByExample(example);
            if(!CollectionUtils.isEmpty(childModuleList)){
                for(SysAclModule module : childModuleList) {
                    String level = module.getLevel();
                    //替换所有子模块的level前缀
                    if(level.indexOf(oldLevelPrefix) == 0){
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        module.setLevel(level);
                    }
                }
                //TODO 批量更新子模块
                for(SysAclModule module : childModuleList) {
                    moduleMapper.updateByPrimaryKeySelective(module);
                }
            }
        }
        //更新当前模块信息
        moduleMapper.updateByPrimaryKeySelective(after);
    }

    @Override
    public void delete(Integer aclModuleId) {
        //判断待删除的模块是否存在
        SysAclModule module = moduleMapper.selectByPrimaryKey(aclModuleId);
        if(Objects.isNull(module)) {
            throw new ParameterException("待删除的模块不存在");
        }

        //判断当前模块下是否有子模块
        SysAclModuleExample example = new SysAclModuleExample();
        SysAclModuleExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(aclModuleId);
        if(moduleMapper.countByExample(example) > 0) {
            throw new ParameterException("待删除部门下存在子部门，删除失败");
        }
        //查询当前模块下是否存在权限资源
        SysAclExample aclExample = new SysAclExample();
        SysAclExample.Criteria criteria1 = aclExample.createCriteria();
        criteria1.andAclModuleIdEqualTo(aclModuleId);
        if(aclMapper.countByExample(aclExample) > 0) {
            throw new ParameterException("待删除部门下存在权限资源，删除失败");
        }
        //删除
        moduleMapper.deleteByPrimaryKey(aclModuleId);
    }


    //检查模块是否已经存在
    private boolean checkExist(String moduleName, Integer parentId, Integer moduleId){
        SysAclModuleExample example = new SysAclModuleExample();
        SysAclModuleExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(moduleName);
        criteria.andParentIdEqualTo(parentId);
        if(!Objects.isNull(moduleId)) {
            criteria.andIdNotEqualTo(moduleId);
        }
        return moduleMapper.selectByExample(example).size() > 0;
    }

    //根据模块id获取level
    private String getLevel(Integer aclModuleId){
        SysAclModule aclModule = moduleMapper.selectByPrimaryKey(aclModuleId);
        if(Objects.isNull(aclModule)){
            return null;
        }
        return aclModule.getLevel();
    }
}
