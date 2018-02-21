package com.learn.permission.permission.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserParam {

    private Integer id;
    private String username;
    private String telephone;
    private String mail;
    private Integer deptId;
    private Integer status;
    private String remark;

}
