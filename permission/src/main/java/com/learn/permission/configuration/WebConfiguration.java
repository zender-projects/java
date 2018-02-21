package com.learn.permission.configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.learn.permission.common.interceptor.HttpInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import java.util.HashMap;
import java.util.Map;

@SpringBootConfiguration
@ComponentScans({
        @ComponentScan("com.learn.permission.common.exception"),  //统一异常处理
        @ComponentScan("com.learn.permission.common.interceptor") //Http拦截器
})
@Slf4j
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private HttpInterceptor httpInterceptor;

    //Druid Monitor Servlet
    @Bean
    public ServletRegistrationBean registServlet(){
        StatViewServlet druidServlet = new StatViewServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(druidServlet);
        registrationBean.addUrlMappings("/sys/druid/*");
        Map<String, String> initParams = new HashMap<String, String>();
        initParams.put("loginUsername", "druid");
        initParams.put("loginPassword", "druid");
        registrationBean.setInitParameters(initParams);

        return registrationBean;
    }

    //Druid Monitor Filter
    @Bean
    public FilterRegistrationBean registFilter(){
        WebStatFilter druidFilter = new WebStatFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(druidFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/sys/druid/*");

        return registrationBean;
    }


    //开启默认Sevlet
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpInterceptor);
        log.info("拦截器注册完成:[{}]", httpInterceptor.getClass().getName());
    }


    //thymeleaf resolver
    /*@Bean
    public TemplateResolver templateResolver(){
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
        templateResolver.setCacheable(false);
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setPrefix("classpath:/templates");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }
    @Bean
    public SpringTemplateEngine springTemplateEngine(TemplateResolver templateResolver) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver);
        return  engine;
    }
    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine engine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(engine);
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }*/



}
