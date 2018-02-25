package com.learn.permission.permission.param;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class RoleParam {

    private Integer id;
    private String name;
    private Integer type = 1;
    private Integer status;
    private String remark;

}
