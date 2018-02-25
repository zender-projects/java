package com.learn.permission.permission.controller;

import com.learn.permission.common.result.JsonDataResult;
import com.learn.permission.common.validate.Validator;
import com.learn.permission.common.validate.rule.Rules;
import com.learn.permission.permission.param.AclModuleParam;
import com.learn.permission.permission.service.SysAclModuleService;
import com.learn.permission.permission.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sys/aclmodule")
@Slf4j
public class SysAclModuleController {

    @Autowired
    private SysAclModuleService moduleService;
    @Autowired
    private SysTreeService treeService;

    @RequestMapping("/acl/page")
    public String aclPage(){
        return "acl";
    }

    @RequestMapping("/save")
    @ResponseBody
    public JsonDataResult save(AclModuleParam aclModuleParam){

        Validator.validate(aclModuleParam.getName(), "name", Rules.REQUIRED());
        Validator.validate(aclModuleParam.getParentId(), "parentId", Rules.REQUIRED());
        Validator.validate(aclModuleParam.getSeq(), "seq", Rules.REQUIRED());
        Validator.validate(aclModuleParam.getStatus(), "status", Rules.REQUIRED());

        moduleService.save(aclModuleParam);
        return JsonDataResult.success();
    }

    @RequestMapping("/update")
    @ResponseBody
    public JsonDataResult update(AclModuleParam aclModuleParam){

        Validator.validate(aclModuleParam.getId(), "id", Rules.REQUIRED());
        Validator.validate(aclModuleParam.getName(), "name", Rules.REQUIRED());
        Validator.validate(aclModuleParam.getParentId(), "parentId", Rules.REQUIRED());
        Validator.validate(aclModuleParam.getSeq(), "seq", Rules.REQUIRED());
        Validator.validate(aclModuleParam.getStatus(), "status", Rules.REQUIRED());

        moduleService.update(aclModuleParam);

        return JsonDataResult.success();
    }


    @RequestMapping("/delete")
    @ResponseBody
    public JsonDataResult delete(@RequestParam("id") Integer id){
        Validator.validate(id, "id", Rules.REQUIRED());
        moduleService.delete(id);

        return JsonDataResult.success();
    }

    @RequestMapping("/tree")
    @ResponseBody
    public JsonDataResult tree(){

        return JsonDataResult.success(treeService.aclModelTree());
    }
}
