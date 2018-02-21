package com.learn.permission;

import com.learn.permission.configuration.RootConfiguration;
import com.learn.permission.configuration.WebConfiguration;
import com.learn.permission.permission.dao.SysDeptMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
@Import({RootConfiguration.class, WebConfiguration.class})
public class PermissionApplication {

    //注册用户：zhangdong2, 密码：F7e4D9v4N
    //注册用户：zhangdd, 密码：U9m3K9p2
    public static void main(String[] args) {
       SpringApplication.run(PermissionApplication.class, args);
    }
}
