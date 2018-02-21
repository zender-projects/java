package com.learn.permission.login.controller;

import com.learn.permission.common.util.MD5Util;
import com.learn.permission.common.validate.Validator;
import com.learn.permission.common.validate.rule.Rules;
import com.learn.permission.login.exception.LoginException;
import com.learn.permission.permission.model.SysUser;
import com.learn.permission.permission.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Controller
@Slf4j
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private SysUserService userService;

    @RequestMapping("/login/page")
    public String toLoginPage(){
        return "signin";
    }

    @RequestMapping(value = "/admin/index")
    public String adminIndexPage(){
        return "views/admin";
    }

    @RequestMapping("/login")
    public void login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        System.out.println(username + ":" + password);

        try {
            /*String username = request.getParameter("username");
            String password = request.getParameter("password");*/
            //log.info("5");
            String callbackUrl = request.getParameter("callback");

            Validator.validate(username, "username", Rules.REQUIRED());
            Validator.validate(password, "password", Rules.REQUIRED());

            SysUser currentUser = userService.queryByKeyword(username);
            String errorMessage = "";
            //log.info("1");
            if (Objects.isNull(currentUser)) {
                //用户不存在
                //log.info("not exist");
                errorMessage = "用户不存在";
            } else if (!currentUser.getPassword().equals(MD5Util.encrypt(password))) {
                //密码错误
                //log.info("username or password error");
                errorMessage = "用户名或密码错误";
            } else if (currentUser.getStatus() != 1) {
                //无效用户
                //log.info("invalid user");
                errorMessage = "用户已被冻结，请联系管理员";
            } else {
                //log.info("登录成功");
                //登录成功
                request.getSession().setAttribute("currentUser", currentUser);
                if (!Objects.isNull(callbackUrl)) {
                    response.sendRedirect(callbackUrl);
                }else{
                    //log.info("3");
                    ///response.sendRedirect("/admin/index");
                    //return "views/admin";
                    response.sendRedirect("/user/admin/index");
                }

            }
            //log.info("2");
            request.setAttribute("error", errorMessage);
            request.setAttribute("username", username);
            if(StringUtils.isNoneBlank(callbackUrl)) {
                request.setAttribute("callback", callbackUrl);
            }
            request.getRequestDispatcher("/user/login/page").forward(request,response);
            //return "signin";
        }catch (Exception ex) {
            //log.info("6");
            throw new LoginException("登录失败，" + ex.getMessage());
        }

        //log.info("7");
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.GET})
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getSession().invalidate();
            response.sendRedirect("/user/login/page");
        }catch (Exception ex) {
            log.info("用户退出失败");
            ex.printStackTrace();
            throw new LoginException("用户退出失败");
        }
    }


}
