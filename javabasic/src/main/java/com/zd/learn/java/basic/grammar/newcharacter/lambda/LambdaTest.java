package com.zd.learn.java.basic.grammar.newcharacter.lambda;

public class LambdaTest {

    public static void main(String[] args) {

        //单行语句，不用{}
        IMessage message = (String msg) -> System.out.println("msg:" + msg);
        message.method("zhangsan");

        //多行语句，用{}即可
        IMessage message1 = (String msg) -> {
            System.out.println("-----");
            System.out.println("aaaaaa");
            System.out.println("-----");
        };


        //带返回值的lambda

        //只有一句处理语句，可以省略return
        IService service = (String msg) -> "[ " + msg + " ]";
        String rs = service.method("zhangdong");
        System.out.println(rs);

        //有多行处理语句的lambda, 添加{}和return语句即可
        IService service1 = (String msg) -> {
            System.out.println("------");
            System.out.println(msg);
            System.out.println("------");
            return msg;

        };


    }
}
