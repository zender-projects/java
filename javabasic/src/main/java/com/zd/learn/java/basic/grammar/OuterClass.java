package com.zd.learn.java.basic.grammar;

import java.util.concurrent.TimeUnit;

/**
 * 内部类.
 * 成员内部类、静态内部类、局部内部类、匿名内部类。
 *
 * 成员访问：内部类无条件访问外部类成员   Outer.this.fieldName
 *          外部类要通过内部类对象访问内部类成员
 *
 * 创建对象：Outer.Inner inner = new Outer().new Inner();
 *
 *
 * @author mac
 * */
public class OuterClass {

    private String code = "Outer Class";

    class InnerClass {
        private String name;
        private Integer age;

        public String getName () { return this.name; }
        public void setName(String name) { this.name = name; }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public void innerMethod() {
            System.out.println("InnerClass.innerMethod()");
            System.out.println("code:" + code);
            System.out.println("OuterClass.this.code:" + OuterClass.this.code);
        }
    }

    public void outerMethod () {
        System.out.println("OuterClass.outerMethod()");

    }

    public void outerMethod2(final String param1, final String param2) {

        /**
         * 匿名内部类和局部内部类 在访问外部类参数时，外部类参数都必须为final的。
         * */

        new Thread(new Runnable() {
            public void run() {
                while(true) {
                    try {

                        TimeUnit.SECONDS.sleep(10);
                        System.out.println(param1);

                    }catch(Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();


        class A {

            public void print() {
                System.out.println(param2);
            }
        }

    }

    public static void main(String[] args) {

        //创建内部类对象
        OuterClass.InnerClass innerClass = new OuterClass().new InnerClass();

        innerClass.setName("inner class");
        System.out.println(innerClass.getName());

        innerClass.innerMethod();


    }


}
