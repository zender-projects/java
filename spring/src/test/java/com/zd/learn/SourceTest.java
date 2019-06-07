package com.zd.learn;

import org.springframework.aop.aspectj.AspectJMethodBeforeAdvice;
import org.springframework.aop.config.AopNamespaceHandler;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ResourceLoader;

public class SourceTest{

    public void test1() {

        //BeanFactory
        //XmlBeanFactory
        //BeanFactory
        //ListableBeanFactory
        //BeanDefinitionReader

        //AbstractBeanFactory

        //ResourceLoader
        //DefaultListableBeanFactory
        //BeanDefinitionReader
        //XmlBeanDefinitionReader
        //AopNamespaceHandler
        //BeanDefinitionParserDelegate
        //nternalAutoProxyCreator
        //AbstractAutoProxyCreator
        //AspectJMethodBeforeAdvice

        //Abstract

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
    }
}
