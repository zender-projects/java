package com.learn.permission.login.configuration;

import com.learn.permission.configuration.bean.MapperLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class LoginMapperLoader implements MapperLoader{
    @Override
    public Resource[] load() {
        return new Resource[]{
            //new ClassPathResource("mapper/login/*.xml")
        };
    }

    @Override
    public String aliasPackage() {
        return "com.learn.permission.login.model";
    }
}
