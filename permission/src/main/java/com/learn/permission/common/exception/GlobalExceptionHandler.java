package com.learn.permission.common.exception;

import com.learn.permission.common.result.JsonDataResult;
import com.learn.permission.login.exception.LoginException;
import com.learn.permission.permission.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    //权限异常
    @ExceptionHandler(PermissionException.class)
    @ResponseBody
    public JsonDataResult permissionException(HttpServletRequest request, Exception ex){

        String exMessage = "异常信息 >>> 异常名称:" + ex.getClass() + "||" +
                "方法名称：" + ex.getStackTrace()[0].getMethodName() + "||" +
                "类名:" + ex.getStackTrace()[0].getClassName() + "||" +
                "行数:" + ex.getStackTrace()[0].getLineNumber();

        log.error("permission exception[{}]", exMessage);

        return JsonDataResult.fail("权限异常:["+ exMessage +"]");
    }

    //登录异常
    @ExceptionHandler(LoginException.class)
    @ResponseBody
    public JsonDataResult loginException(HttpServletRequest request, Exception ex) {

        String exMessage = "异常信息 >>> 异常名称:" + ex.getClass() + "||" +
                "方法名称：" + ex.getStackTrace()[0].getMethodName() + "||" +
                "类名:" + ex.getStackTrace()[0].getClassName() + "||" +
                "行数:" + ex.getStackTrace()[0].getLineNumber();

        log.error("login exception[{}]",exMessage);

        return JsonDataResult.fail("登录异常:["+ exMessage +"]");
    }

    //参数异常
    @ExceptionHandler(ParameterException.class)
    @ResponseBody
    public JsonDataResult parameterException(HttpServletRequest request, Exception ex) {
        String exMessage = "异常信息 >>> 异常名称:" + ex.getClass() + "||" +
                "方法名称：" + ex.getStackTrace()[0].getMethodName() + "||" +
                "类名:" + ex.getStackTrace()[0].getClassName() + "||" +
                "行数:" + ex.getStackTrace()[0].getLineNumber();

        log.error("parameter exception[{}]", exMessage);
        return JsonDataResult.fail("参数异常:[" + ex.getMessage() + "]");
    }


    //系统业务异常
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public JsonDataResult unkonwException(HttpServletRequest request, Exception ex){
        String exMessage = "异常信息 >>> 异常名称:" + ex.getClass() + "||" +
                "方法名称：" + ex.getStackTrace()[0].getMethodName() + "||" +
                "类名:" + ex.getStackTrace()[0].getClassName() + "||" +
                "行数:" + ex.getStackTrace()[0].getLineNumber();
        ex.printStackTrace();

        log.error("unknow exception[{}]", exMessage);
        return JsonDataResult.fail("System Error");
    }

}
