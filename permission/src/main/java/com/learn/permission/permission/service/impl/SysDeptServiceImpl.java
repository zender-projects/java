package com.learn.permission.permission.service.impl;

import com.learn.permission.common.exception.ParameterException;
import com.learn.permission.common.util.LevelUtil;
import com.learn.permission.permission.dao.SysDeptMapper;
import com.learn.permission.permission.dao.SysUserMapper;
import com.learn.permission.permission.model.SysDept;
import com.learn.permission.permission.model.SysDeptExample;
import com.learn.permission.permission.model.SysUser;
import com.learn.permission.permission.model.SysUserExample;
import com.learn.permission.permission.param.DeptParam;
import com.learn.permission.permission.service.SysDeptService;
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
public class SysDeptServiceImpl implements SysDeptService{

    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private SysUserMapper userMapper;

    @Override
    public Integer save(DeptParam deptParam) {
        if(checkDeptNameExist(deptParam.getParentId(), deptParam.getName(), deptParam.getId())){
            throw new ParameterException("同一部门下部门名称已存在");
        }
        SysDept dept = SysDept.builder().name(deptParam.getName())
                                        .parentId(deptParam.getParentId())
                                        .seq(deptParam.getSeq())
                                        .remark(deptParam.getRemark()).build();

        dept.setLevel(LevelUtil.calculateLevel(getLevel(deptParam.getParentId()), deptParam.getParentId()));
        //TODO
        dept.setOperator("system");
        dept.setOperateIp("localhost");
        dept.setOperateTime(new Date());

        //save
        return deptMapper.insertSelective(dept);
    }

    @Override
    public void update(DeptParam deptParam) {

        //查询更新之前的部门信息
        SysDept before = deptMapper.selectByPrimaryKey(deptParam.getId());
        if(Objects.isNull(before)){
           throw new ParameterException("待更新待部门不存在");
        }
        //检查名称是否有变化-发生变化
        if(!before.getName().equals(deptParam.getName())){
            //检查名称是否存在
            if(checkDeptNameExist(deptParam.getParentId(), deptParam.getName(), deptParam.getId())){
                throw new ParameterException("部门名称已存在");
            }
        }

        //构建部门信息
        SysDept updateDept = SysDept.builder().id(deptParam.getId())
                                              .name(deptParam.getName())
                                              .parentId(deptParam.getParentId())
                                              .seq(deptParam.getSeq())
                                              .remark(deptParam.getRemark()).build();

        updateDept.setLevel(LevelUtil.calculateLevel(getLevel(deptParam.getParentId()), deptParam.getParentId()));
        //TODO
        updateDept.setOperator("system");
        updateDept.setOperateIp("localhost");
        updateDept.setOperateTime(new Date());

        //更新
        updateDeptAndChildDept(updateDept, before);
    }

    @Override
    public void delete(Integer deptId) {
        SysDept dept = deptMapper.selectByPrimaryKey(deptId);
        if(dept == null) {
            throw new ParameterException("待删除待部门不存在, 无法删除");
        }
        SysDeptExample example1 = new SysDeptExample();
        SysDeptExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andParentIdEqualTo(deptId);
        if(deptMapper.selectByExample(example1).size() > 0){
            throw new ParameterException("待删除的部门下存在子部门，无法删除");
        }
        SysUserExample example2 = new SysUserExample();
        SysUserExample.Criteria criteria2 = example2.createCriteria();
        criteria2.andDeptIdEqualTo(deptId);
        if(userMapper.selectByExample(example2).size() > 0){
            throw new ParameterException("待删除的部门下存在用户，无法删除");
        }
        deptMapper.deleteByPrimaryKey(deptId);
    }

    @Transactional
    private void updateDeptAndChildDept(SysDept currentDept, SysDept oldDept) {
        String currentDeptLevel = currentDept.getLevel();
        String oldDeptLevel = oldDept.getLevel();
        //如果level不相同，说明是跨部门更新，子部门也需要随之改变，包括parentId和level信息
        if(!currentDept.getLevel().equals(oldDept.getLevel())) {
            //获取所有待变更的部门信息
            SysDeptExample oldDeptListExample = new SysDeptExample();
            SysDeptExample.Criteria oldDeptListCriteria = oldDeptListExample.createCriteria();
            //oldDeptListCriteria.andLevelEqualTo(oldDept.getLevel());
            //前缀相同，即所有的子部门
            oldDeptListCriteria.andLevelLike(oldDept.getLevel() + ".%");
            List<SysDept> oldDepts = deptMapper.selectByExample(oldDeptListExample);

            if(!CollectionUtils.isEmpty(oldDepts)){
                for(SysDept dept : oldDepts) {
                  String level = dept.getLevel();
                  if(level.indexOf(oldDeptLevel) == 0) {
                      //新的level前缀 + 去掉老的level前缀的后面的部分
                      level = currentDeptLevel + level.substring(oldDeptLevel.length());
                      dept.setLevel(level);
                  }
                }
                //TODO 批量更新
                for(SysDept dept : oldDepts) {
                    deptMapper.updateByPrimaryKeySelective(dept);
                }
            }
        }
        //deptMapper.updateByPrimaryKey(currentDept);
        deptMapper.updateByPrimaryKeySelective(currentDept);
    }

    private boolean checkDeptNameExist(Integer parentId, String deptName, Integer deptId) {

        SysDeptExample example = new SysDeptExample();
        SysDeptExample.Criteria criteria = example.createCriteria();

        criteria.andParentIdEqualTo(parentId);
        criteria.andNameEqualTo(deptName);

        //update
        if(deptId != null){
            //criteria.andIdEqualTo(deptId);
            criteria.andIdNotEqualTo(deptId);
        }

        List<SysDept> depts = deptMapper.selectByExample(example);

        return depts.size() > 0;
    }

    //获取部门级别
    private String getLevel(Integer deptId) {
        SysDept dept = deptMapper.selectByPrimaryKey(deptId);
        if(dept == null) {
            return null;
        }
        return dept.getLevel();
    }

    /*@Autowired
    private SysDeptDao deptDao;

    @Override
    public List<SysDept> queryAll() {
        return deptDao.selectAll();
    }*/
}
