package com.learn.permission.permission.controller;

import com.learn.permission.common.result.JsonDataResult;
import com.learn.permission.common.validate.Validator;
import com.learn.permission.common.validate.rule.Rules;
import com.learn.permission.permission.param.UserParam;
import com.learn.permission.permission.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @RequestMapping(value = "/save", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonDataResult save(UserParam user){

        Validator.validate(user.getUsername(), "username", Rules.REQUIRED());
        Validator.validate(user.getTelephone(), "telephone", Rules.REQUIRED());
        Validator.validate(user.getMail(), "mail", Rules.REQUIRED());
        Validator.validate(user.getDeptId(), "deptId", Rules.REQUIRED());
        Validator.validate(user.getStatus(), "status", Rules.REQUIRED());

        userService.save(user);
        return JsonDataResult.success();
    }


    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JsonDataResult uupdate(UserParam user) {
        userService.update(user);
        return JsonDataResult.success();
    }
}
