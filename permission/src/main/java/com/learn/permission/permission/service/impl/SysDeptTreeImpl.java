package com.learn.permission.permission.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.learn.permission.common.util.LevelUtil;
import com.learn.permission.permission.dao.SysAclModuleMapper;
import com.learn.permission.permission.dao.SysDeptMapper;
import com.learn.permission.permission.dto.AclModuleLevelDto;
import com.learn.permission.permission.dto.DeptLevelDto;
import com.learn.permission.permission.model.SysAclModule;
import com.learn.permission.permission.model.SysAclModuleExample;
import com.learn.permission.permission.model.SysDept;
import com.learn.permission.permission.model.SysDeptExample;
import com.learn.permission.permission.service.SysTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SysDeptTreeImpl implements SysTreeService{

    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private SysAclModuleMapper moduleMapper;

    @Override
    public List<DeptLevelDto> deptTree() {
        //查询所有的部门数据
        List<SysDept> depts = deptMapper.selectByExample(new SysDeptExample());

        List<DeptLevelDto> levelDtos = new ArrayList<>();
        depts.stream().forEach(dept -> {
            DeptLevelDto dto = DeptLevelDto.adapt(dept);
            levelDtos.add(dto);
        });

        return deptListToTree(levelDtos);
    }



    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelDtoList) {

        if(CollectionUtils.isEmpty(deptLevelDtoList)) {
            return Collections.EMPTY_LIST;
        }
        //level - [dept1 dept2]
        //MultiValueMap<String, DeptLevelDto> levelDtoMultiValueMap = new LinkedMultiValueMap<>();
        Multimap<String, DeptLevelDto> levelDtoMultimap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = new ArrayList<DeptLevelDto>();

        //归类
        deptLevelDtoList.stream().forEach(dto -> {
            levelDtoMultimap.put(dto.getLevel(), dto);
            if(LevelUtil.ROOT.equals(dto.getLevel())){
                rootList.add(dto);
            }
        });

        //按照seq排序
        Collections.sort(rootList, new Comparator<DeptLevelDto>() {
            @Override
            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });

        //递归生成树
        transformDeptTree(rootList, LevelUtil.ROOT, levelDtoMultimap);

        return rootList;
    }

    public void transformDeptTree(List<DeptLevelDto> deptLevelDtoList,
                                  String level,
                                  Multimap<String, DeptLevelDto> multimap){
        for(int i = 0;i < deptLevelDtoList.size();i ++) {
            //便利当前层的每一个元素
            DeptLevelDto deptLevelDto = deptLevelDtoList.get(i);
            //下一个层级的level值
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());

            //获取下一层的所有节点
            List<DeptLevelDto> tempDeptLevelDtos = (List<DeptLevelDto>)multimap.get(nextLevel);
            if(!CollectionUtils.isEmpty(tempDeptLevelDtos)) {
                //对当前层部门列表进行排序
                Collections.sort(tempDeptLevelDtos, new Comparator<DeptLevelDto>() {
                    @Override
                    public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                        return o1.getSeq() - o2.getSeq();
                    }
                });
                //设置当前部门对下一级部门列表
                deptLevelDto.setDeptList(tempDeptLevelDtos);
                //进入到下一个层级到处理
                transformDeptTree(tempDeptLevelDtos, nextLevel, multimap);
            }
        }
    }


    @Override
    public List<AclModuleLevelDto> aclModelTree() {
        //查询所有模块信息
        List<SysAclModule> modules = moduleMapper.selectByExample(new SysAclModuleExample());

        //转换成Dto Bean
        List<AclModuleLevelDto> dtos = new ArrayList<>();
        for(SysAclModule module : modules) {
            dtos.add(AclModuleLevelDto.adapt(module));
        }
        return null;
    }



    //将AclModuleDtoList 转换成tree
    public List<AclModuleLevelDto> aclModuleLevelDtoListToTree(List<AclModuleLevelDto> moduleLevelDtos){
        //判断非空
        if(CollectionUtils.isEmpty(moduleLevelDtos)){
            return Collections.emptyList();
        }
        //level -> [m1, m2 m3], leve2-> [m4, m5, m6]
        Multimap<String, AclModuleLevelDto> levelDtoMultimap = ArrayListMultimap.create();

        //root module list
        List<AclModuleLevelDto> rootModuleList = new ArrayList<>();
        for(AclModuleLevelDto moduleLevelDto : moduleLevelDtos) {
            levelDtoMultimap.put(moduleLevelDto.getLevel(), moduleLevelDto);  //同级别下的共享一个key
            //过滤成顶级Module
            if(LevelUtil.ROOT.equals(moduleLevelDto.getLevel())){
                rootModuleList.add(moduleLevelDto);
            }
        }
        //排序
        Collections.sort(rootModuleList, new Comparator<AclModuleLevelDto>() {
            @Override
            public int compare(AclModuleLevelDto o1, AclModuleLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });

        transformAclModuleTree(rootModuleList, LevelUtil.ROOT, levelDtoMultimap);
        return rootModuleList;
    }


    //递归转换
    public void transformAclModuleTree(List<AclModuleLevelDto> dtoList, String level,
                                       Multimap<String, AclModuleLevelDto> levelDtoMultimap){

        for(int i = 0;i < dtoList.size();i ++) {
            AclModuleLevelDto dto = dtoList.get(i);
            //下一个层级的id
            String nextLevel = LevelUtil.calculateLevel(level, dto.getId());
            //获取下一级的module列表
            List<AclModuleLevelDto> childModuleDtoList = (List<AclModuleLevelDto> )levelDtoMultimap.get(nextLevel);
            if(!CollectionUtils.isEmpty(childModuleDtoList)){
                Collections.sort(childModuleDtoList, new Comparator<AclModuleLevelDto>() {
                    @Override
                    public int compare(AclModuleLevelDto o1, AclModuleLevelDto o2) {
                        return o1.getSeq() - o2.getSeq();
                    }
                });
                dto.setAclModuleList(childModuleDtoList);
                transformAclModuleTree(childModuleDtoList, nextLevel, levelDtoMultimap);
            }

        }

    }



    @Override
    public List<AclModuleLevelDto> roleTree() {

        //查询当前用户已分配的权限点

        //查当前角色分配的权限点


        return null;
    }
}
