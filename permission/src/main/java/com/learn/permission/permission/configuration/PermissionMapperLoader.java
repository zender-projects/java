package com.learn.permission.permission.configuration;

import com.learn.permission.configuration.bean.MapperLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapperLoader implements MapperLoader
{
    @Override
    public Resource[] load() {
        return new Resource[]{
            new ClassPathResource("mapper/permission/SysDeptMapper.xml"),
            new ClassPathResource("mapper/permission/SysUserMapper.xml")
        };
    }

    @Override
    public String aliasPackage() {
        return "com.learn.permission.permission.model";
    }
}
