package com.learn.mp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.learn.mp.entity.User;
import com.learn.mp.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootConfiguration
@SpringBootApplication
@MapperScan("com.learn.mp.mapper")
public class MpApplication {

    @Autowired
    private UserMapper userMapper;

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MpApplication.class, args);
        UserMapper userMapper = applicationContext.getBean(UserMapper.class);

        //查询
        //userMapper.selectList(null).forEach(System.out::print);

        //新增，字段遵循驼峰模式，表名称也遵循驼峰模型
        //ID默认为mp的雪花自增模型
        //表明可以通过注解进行修改  @TableName("user")
        //可以通过注解修改主键名称不是id的表（mp默认是id）：@TableId("id")
        //可以通过注解修改bean名称与表中列名称的对应关系：@TableField("name")

        /** 排除非表字段 */
        // 1. 将字段声明为transient
        // 2. 将字段生命为static方法
        // 3. @TableField(exist = false) 生命变量


        /*User user = new User();
        user.setName("lis");
        user.setEmail("lisi@qq.com");
        user.setAge(19);
        int row = userMapper.insert(user);
        System.out.println(row);*/


        select(userMapper);
    }


    //查询
    public static void select(UserMapper userMapper) {

        User user = userMapper.selectById(1);
        System.out.println(user);

        List<User> userList = userMapper.selectBatchIds(Arrays.asList(1, 2));
        userList.stream().forEach(System.out::println);

        System.out.println();
        Map<String, Object> param1 = new HashMap<>();
        //name 为数据库中的列名称， 不是bean中的属性名称
        param1.put("name","Billie"); // name =  and age =
        param1.put("age",24);
        List<User> userList1 = userMapper.selectByMap(param1);
        userList1.stream().forEach(System.out::println);

        /**条件构造器查询 Wraper*/

        //QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        System.out.println();
        QueryWrapper<User> queryWrapper = Wrappers.query();
        // name like "%J%" and ag = 40
        queryWrapper.like("name","J").lt("age",40);
        List<User> userList2 = userMapper.selectList(queryWrapper);
        userList2.stream().forEach(System.out::println);


        //name like '%j%' and aget between 10 and 40 and email is not null
        System.out.println();
        QueryWrapper<User> queryWrapper1 = Wrappers.query();
        queryWrapper1.like("name","J").between("age", 20, 40).isNotNull("email");
        List<User> userList3 = userMapper.selectList(queryWrapper1);
        userList3.stream().forEach(System.out::println);

        System.out.println();
        //name like 'J%' or aget >= 40 order by age desc, id asc
        QueryWrapper<User> queryWrapper2 = Wrappers.query();
        queryWrapper2.likeRight("name","J").or().ge("age",40).orderByDesc("age").orderByAsc("id");
        List<User> userList4 = userMapper.selectList(queryWrapper1);
        userList4.stream().forEach(System.out::println);

    }

}
