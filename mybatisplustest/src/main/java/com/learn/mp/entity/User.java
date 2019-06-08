package com.learn.mp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class User {
    @TableId("id")
    private Long id;
    @TableField("name")
    private String name;
    private Integer age;
    private String email;

    //非表字段
    //private transient String remark;
    //private static String remark;
    @TableField(exist = false)
    private String remark;
}
