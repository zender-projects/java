package com.learn.permission.configuration.bean;

import org.springframework.core.io.Resource;

public interface MapperLoader {

    Resource[] load();

    String aliasPackage();
}
