package com.learn.permission.permission.param;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AclParam{

    private Integer id;
    private String name;
    private Integer aclModuleId;
    private String url;
    private Integer type;
    private Integer status;
    private Integer seq;
    private String remark;

}
