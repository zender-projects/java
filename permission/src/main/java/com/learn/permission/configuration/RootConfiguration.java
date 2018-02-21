package com.learn.permission.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.learn.permission.configuration.bean.DBBean;
import com.learn.permission.configuration.bean.MapperLoader;
import com.learn.permission.login.configuration.LoginConfiguration;
import com.learn.permission.permission.configuration.PermissionConfiguration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 配置类.
 * @author mac
 * */
@SpringBootConfiguration
@ComponentScans(
        {
                @ComponentScan("com.learn.permission.configuration.bean")
        }
)
@Import({LoginConfiguration.class, PermissionConfiguration.class})
public class RootConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(RootConfiguration.class);

    @Autowired
    private DBBean dbBean;

    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource createDataSource(){
        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setDriverClassName(dbBean.getDriver());
        dataSource.setUrl(dbBean.getUrl());
        dataSource.setUsername(dbBean.getUsername());
        dataSource.setPassword(dbBean.getPassword());

        return dataSource;
    }


    @Bean
    public SqlSessionFactoryBean createSqlSessionFactoryBean(DataSource dataSource,
                                                             ApplicationContext applicationContext){
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

        factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        factoryBean.setDataSource(dataSource);

        Map<String, MapperLoader> loaderBeans = applicationContext.getBeansOfType(MapperLoader.class);

        if(!CollectionUtils.isEmpty(loaderBeans)){
            Collection<MapperLoader> loaders = loaderBeans.values();
            //Mapper.xml
            List<Resource> resourceList = loaders.stream().flatMap(
                    loader -> Arrays.asList(loader.load()).stream()
            ).map(loader -> { return (Resource)loader;}).collect(Collectors.toList());

            //logger.debug("resources:--------");
            resourceList.stream().forEach(a -> {
                System.out.println(a.getClass().getName());
            });

            Resource[] resources = resourceList.toArray(new Resource[resourceList.size()]);
            factoryBean.setMapperLocations(resources);

            //type alias package
            String typeAliasPackages = loaders.stream().map(loader -> loader.aliasPackage()).collect(Collectors.joining(",")).toString();
            factoryBean.setTypeAliasesPackage(typeAliasPackages);
        }

        return factoryBean;
    }

}
