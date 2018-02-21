package com.learn.permission;

import com.learn.permission.configuration.RootConfiguration;
import com.learn.permission.configuration.WebConfiguration;
import com.learn.permission.permission.dao.SysDeptMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
@Import({RootConfiguration.class, WebConfiguration.class})
public class PermissionApplication {


    public static void main(String[] args) {
       ApplicationContext context = SpringApplication.run(PermissionApplication.class, args);

      /* DataSource dataSource = context.getBean(DataSource.class);
       System.out.println(dataSource.getClass().getName());

       Collection<MapperLoader> loaders = context.getBeansOfType(MapperLoader.class).values();

       loaders.stream().flatMap(mapperLoader -> Arrays.asList(mapperLoader.load()).stream())
       .forEach(mapper -> {
           System.out.println(mapper);
       });

       String ps = loaders.stream().map(p -> p.aliasPackage()).collect(Collectors.joining(",")).toString();
        System.out.println(ps);*/

        //SysDeptMapper deptDao = context.getBean(SysDeptMapper.class);
       // List<SysDept> depts = deptDao.selectAll();
        //System.out.println(depts);
    }
}
