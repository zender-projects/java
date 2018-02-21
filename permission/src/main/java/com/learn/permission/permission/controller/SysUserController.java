package com.learn.permission.permission.controller;

import com.learn.permission.common.bean.PageQuery;
import com.learn.permission.common.result.JsonDataResult;
import com.learn.permission.common.result.PageDataResult;
import com.learn.permission.common.validate.Validator;
import com.learn.permission.common.validate.rule.Rules;
import com.learn.permission.permission.model.SysUser;
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
    public JsonDataResult update(UserParam user) {

        Validator.validate(user.getId(), "id", Rules.REQUIRED());

        userService.update(user);
        return JsonDataResult.success();
    }


    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JsonDataResult delete(@RequestParam("id") Integer userId) {

        Validator.validate(userId, "id", Rules.REQUIRED());

        userService.delete(userId);
        return JsonDataResult.success();
    }


    @RequestMapping(value = "/page", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public PageDataResult<SysUser> pageQuery(Integer deptId, PageQuery pageQuery) {

        Validator.validate(deptId, "deptId", Rules.REQUIRED());

        PageDataResult<SysUser> pageDataResult = userService.getPageByDeptId(deptId, pageQuery);
        return pageDataResult;
    }
}
