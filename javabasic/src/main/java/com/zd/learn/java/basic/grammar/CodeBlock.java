package com.zd.learn.java.basic.grammar;

/**
 * 代码块:
 *
 * 局部代码块、对象代码块、静态代码块
 *
 * 调用顺序：静态代码块->对象代码块->构造函数->局部代码块
 *
 * 作用：用做预初始化
 * */
public class CodeBlock {

    private String name;
    private Integer age;

    private static String code;
    //类代码块，创建对象时调用，优先于构造函数被调用
    {
        System.out.println("类代码块");
        name = "mac";
        age = 12;

        System.out.println("name:" + this.name + ", age:" + this.age);
    }


    public CodeBlock () {
        System.out.println("构造函数");
    }

    //静态代码块，类被加载时调用
    static {
        System.out.println("静态代码块");
        code = "Code Block";
        System.out.println("code:" + CodeBlock.code);
    }

    public void print(Object object) {

        //方法内的局部代码块，基本没用
        {
            System.out.println("局部代码块");
            int x = 10;
            System.out.println(x);
        }

        int x = 12;
        System.out.println(12);
    }

    public static void main(String[] args) {

        CodeBlock codeBlock = new CodeBlock();
        codeBlock.print("code block");

    }
}
