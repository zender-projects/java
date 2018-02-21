package com.learn.permission.configuration.bean;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Data
@Component
@ConfigurationProperties(prefix = "db")
public class DBBean {
    @NotNull
    private String driver;
    @NotNull
    private String url;
    @NotNull
    private String username;
    @NotNull
    private String password;
}
