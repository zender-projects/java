package com.learn.permission.common.interceptor;

import com.learn.permission.common.util.JsonMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Component
public class HttpInterceptor extends HandlerInterceptorAdapter{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        Map<String, String[]> params = request.getParameterMap();

        log.info("request start. thread:{}, url:{}, params:{}",
                Thread.currentThread().getName(),
                url,
                JsonMapperUtil.obj2String(params));

        long startTime = System.currentTimeMillis();
        request.setAttribute("requestStartTime", startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String url = request.getRequestURI();
        long startTime = (Long)request.getAttribute("requestStartTime");
        long endTime = System.currentTimeMillis();

        log.info("request completed. thread:{}, url:{}, cost:{}",
                    Thread.currentThread().getName(),
                    url,
                    (endTime - startTime));
    }
}
