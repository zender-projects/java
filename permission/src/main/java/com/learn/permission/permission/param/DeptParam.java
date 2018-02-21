package com.learn.permission.permission.param;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DeptParam {

    private Integer id;
    private String name;
    private Integer parentId;
    private Integer seq;
    private String remark;
}
