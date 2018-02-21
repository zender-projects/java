package com.learn.permission.common.bean;

import com.learn.permission.permission.model.SysUser;

import javax.servlet.http.HttpServletRequest;

public class RequestHolder {

    private final static ThreadLocal<SysUser> userHolder = new ThreadLocal<SysUser>();

    private final static ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();

    public static void add(SysUser user) {
        userHolder.set(user);
    }

    public static void add(HttpServletRequest request) {
        requestHolder.set(request);
    }

    public static SysUser getCurrentUser(){
        return userHolder.get();
    }

    public static HttpServletRequest getCurrentRequest(){
        return requestHolder.get();
    }

    public static void remove(){
        userHolder.remove();
        requestHolder.remove();
    }

}
