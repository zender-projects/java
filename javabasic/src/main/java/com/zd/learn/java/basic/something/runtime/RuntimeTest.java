package com.zd.learn.java.basic.something.runtime;

/***
 *  Runtime 描述运行时。在每个jvm中有一个实例对象。
 *
 *  Runtime为 饿汉式单例模式。
 */
public class RuntimeTest {

    public static void main(String[] args) {

        //获取Runtime实例
        Runtime runtime = Runtime.getRuntime();

        //内存信息
        long freeMemory = runtime.freeMemory();    //剩余内存空间
        long totalMemory = runtime.totalMemory();  //总内存空间

        long maxMemory = runtime.maxMemory();      //最大内存空间

        System.out.println("[free: " + freeMemory +", total:, " + totalMemory +", max " + maxMemory + "]");

    }
}
