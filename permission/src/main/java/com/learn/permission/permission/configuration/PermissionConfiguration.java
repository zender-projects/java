package com.learn.permission.permission.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootConfiguration
@ComponentScans({
        @ComponentScan("com.learn.permission.permission.configuration"),
        @ComponentScan("com.learn.permission.permission.service"),
        @ComponentScan("com.learn.permission.permision.controller")
})
@MapperScan({"com.learn.permission.permission.dao"})
public class PermissionConfiguration {
}
