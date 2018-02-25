package com.learn.permission.permission.controller;

import com.learn.permission.common.bean.PageQuery;
import com.learn.permission.common.result.JsonDataResult;
import com.learn.permission.common.result.PageDataResult;
import com.learn.permission.permission.param.AclParam;
import com.learn.permission.permission.service.SysAclService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sys/acl")
@Slf4j
public class SysAclController {


    @Autowired
    private SysAclService aclService;

    @RequestMapping("/save")
    @ResponseBody
    public JsonDataResult save(AclParam param){
        //TODO parameter validate
        aclService.save(param);
        return JsonDataResult.success();
    }


    @RequestMapping("/save")
    @ResponseBody
    public JsonDataResult update(AclParam param) {
        //TODO parameter validate
        aclService.update(param);
        return JsonDataResult.success();
    }

    @RequestMapping("/page")
    @ResponseBody
    public JsonDataResult pageByModuleId(@RequestParam("moduleId") Integer moduleId, PageQuery pageQuery){
        return JsonDataResult.success((aclService.getPageByAclModuleId(moduleId, pageQuery)));
    }
}
