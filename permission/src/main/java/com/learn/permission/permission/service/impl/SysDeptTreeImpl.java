package com.learn.permission.permission.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.learn.permission.common.util.LevelUtil;
import com.learn.permission.permission.dao.SysDeptMapper;
import com.learn.permission.permission.dto.DeptLevelDto;
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
}
