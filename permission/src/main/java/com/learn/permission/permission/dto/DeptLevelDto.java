package com.learn.permission.permission.dto;

import com.learn.permission.permission.model.SysDept;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class DeptLevelDto extends SysDept{

    private List<DeptLevelDto> deptList = new ArrayList<>();

    public static DeptLevelDto adapt(SysDept dept){
        DeptLevelDto deptLevelDto = new DeptLevelDto();

        BeanUtils.copyProperties(dept, deptLevelDto);

        return deptLevelDto;
    }
}
