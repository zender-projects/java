package com.learn.concurrency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    //压力测试接口
    @GetMapping("/test")
    public String test(){
        log.info("request url /test");
        return "test";
    }

}
