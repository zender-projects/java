package com.learn.permission.permission.controller;

import com.learn.permission.common.result.JsonDataResult;
import com.learn.permission.common.validate.Validator;
import com.learn.permission.common.validate.rule.Rules;
import com.learn.permission.permission.dto.DeptLevelDto;
import com.learn.permission.permission.param.DeptParam;
import com.learn.permission.permission.service.SysDeptService;
import com.learn.permission.permission.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/sys/dept")
public class SysDeptController {


    @Autowired
    private SysDeptService service;
    @Autowired
    private SysTreeService treeService;


    @RequestMapping(value = "/page")
    public String deptPage(){
        return "dept";
    }


    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    @ResponseBody
    public JsonDataResult saveDept(DeptParam deptParam) {

        Validator.validate(deptParam.getName(), "name", Rules.REQUIRED());
        Validator.validate(deptParam.getParentId(), "parentId", Rules.REQUIRED());
        Validator.validate(deptParam.getSeq(), "seq", Rules.REQUIRED());
        //Validator.validate(deptParam.getRemark(), "remark", Rules.REQUIRED());

        service.save(deptParam);

        return JsonDataResult.success();
    }


    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ResponseBody
    public JsonDataResult updateDept(DeptParam deptParam) {

        //log.info("udpate parameter:{}", deptParam);

        Validator.validate(deptParam.getId(), "id", Rules.REQUIRED());
        Validator.validate(deptParam.getName(), "name", Rules.REQUIRED());
        Validator.validate(deptParam.getParentId(), "parentId", Rules.REQUIRED());
        Validator.validate(deptParam.getSeq(), "seq", Rules.REQUIRED());
        //Validator.validate(deptParam.getRemark(), "remark", Rules.REQUIRED(), Rules.MAX(150));

        service.update(deptParam);

        return JsonDataResult.success();
    }


    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonDataResult deleteDept(@RequestParam("id") Integer deptId){

        Validator.validate(deptId, "deptId", Rules.REQUIRED());

        service.delete(deptId);

        return JsonDataResult.success();
    }



    @RequestMapping(value = "/tree", method = {RequestMethod.GET})
    @ResponseBody
    public JsonDataResult deptTree(){
        List<DeptLevelDto> tree = treeService.deptTree();
        return JsonDataResult.success(tree);
    }

}
