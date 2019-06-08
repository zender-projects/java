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

        //date_format(create_time, '%'Y-%m-%d) and id in (select id from user where name like 'J%')
        System.out.println();
        QueryWrapper<User> queryWrapper3 = Wrappers.query();
        queryWrapper3.apply("date_format(create_time, '%Y-%m-%d') = {0}","2019-06-08")
                .inSql("id","select id from user where name like 'J%'");
        List<User> userList5 = userMapper.selectList(queryWrapper3);
        userList5.stream().forEach(System.out::println);

        System.out.println();
        //name like 'J%' and (age < 40 or email is not null)
        QueryWrapper<User> queryWrapper4 = Wrappers.query();
        //SELECT id,name,age,email FROM user WHERE name LIKE ? AND ( age < ? OR email IS NOT NULL )
        queryWrapper4.likeRight("name","J").and(qw -> qw.lt("age",40).or().isNotNull("email"));
        List<User> userList6 = userMapper.selectList(queryWrapper4);
        userList6.stream().forEach(System.out::println);


        System.out.println();
        //name like 'J%' or (age < 40 or age > 20 and email is not null)
        QueryWrapper<User> queryWrapper5 = Wrappers.query();
        //SELECT id,name,age,email FROM user WHERE name LIKE ? OR ( age < ? AND age > ? AND email IS NOT NULL )
        queryWrapper5.likeRight("name","J").or(qw ->
                qw.lt("age",40)
                        .gt("age",20)
                        .isNotNull("email"));

        List<User> userList7 = userMapper.selectList(queryWrapper5);
        userList7.stream().forEach(System.out::println);



        //(age < 40 or email is not nul) and name like '%J'
        System.out.println();
        QueryWrapper<User> queryWrapper6 = Wrappers.query();
        //SELECT id,name,age,email FROM user WHERE ( age < ? OR email IS NOT NULL ) AND name LIKE ?
        queryWrapper6.nested(qw -> qw.lt("age", 40).or().isNotNull("email"))
                .like("name","J");
        List<User> userList8 = userMapper.selectList(queryWrapper6);
        userList8.stream().forEach(System.out::println);


        //age in (18,20,28)
        //age in (18,20,28)
        QueryWrapper<User> queryWrapper7 = Wrappers.query();
        //SELECT id,name,age,email FROM user WHERE age IN (?,?,?)
        queryWrapper7.in("age", Arrays.asList(18,20,28));
        List<User> userList9 = userMapper.selectList(queryWrapper7);
        userList9.stream().forEach(System.out::println);


        //limit  1
        System.out.println();
        QueryWrapper<User> queryWrapper8 = Wrappers.query();
        queryWrapper8.last("limit 1");
        //SELECT id,name,age,email FROM user limit 1
        List<User> userList10 = userMapper.selectList(queryWrapper8);
        userList10.stream().forEach(System.out::println);


        //返回部分字段
        System.out.println();
        QueryWrapper<User> queryWrapper9 = Wrappers.query();
        queryWrapper9.select("id","name");
        //SELECT id,name,age,email FROM user limit 1
        List<User> userList11 = userMapper.selectList(queryWrapper9);
        userList11.stream().forEach(System.out::println);

        System.out.println();
        QueryWrapper<User> queryWrapper10 = Wrappers.query();
        //排除create_time和age
        //SELECT id,name FROM user
        queryWrapper10.select(User.class, info -> !info.getColumn().equals("create_time") &&
                                info.getColumn().equals("age"));
        //SELECT id,name FROM user
        List<User> userList12 = userMapper.selectList(queryWrapper10);
        userList12.stream().forEach(System.out::println);
    }

}
