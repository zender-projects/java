package com.learn.permission.login.configuration;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootConfiguration
@ComponentScans({
        @ComponentScan("com.learn.permission.login.configuration"),
        @ComponentScan("com.learn.permission.login.controller")
})
@MapperScan({"com.learn.permission.login.dao"})
public class LoginConfiguration {
}
