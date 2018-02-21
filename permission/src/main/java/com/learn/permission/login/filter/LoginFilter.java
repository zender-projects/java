package com.learn.permission.login.filter;

import com.learn.permission.common.bean.RequestHolder;
import com.learn.permission.permission.model.SysUser;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
@Slf4j
@WebFilter(urlPatterns = {"/sys/**", "/admin/**"}, filterName = "loginFilter")
public class LoginFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;


        SysUser currentUser = (SysUser) request.getSession().getAttribute("user");
        log.info("当前登录用户[{}]", currentUser);
        if(Objects.isNull(currentUser)) {

            //跳转到登录页面
            response.sendRedirect("/user/login/page");
            return;
        }
        //将当前登录的用户信息添加到当前线程中
        RequestHolder.add(currentUser);
        RequestHolder.add(request);
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
