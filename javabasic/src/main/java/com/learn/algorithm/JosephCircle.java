package com.learn.algorithm;

/**
 * 约瑟夫环问题.
 * @author mac
 *
问题：有N个人围成一圈，顺序排号。从第一个人开始报数(从1~~3报数),凡报到3的退出圈子，问最后留下的人原来是排在第几号?

请编程实现之。

思路:
(1)用一个循环队列array(用数组或者链表模拟循环队列都可)去存放这个n个人组成的圈子.
(2)然后依次开始报数，用变量currentCallNum表示当前报数（根据题意，从1开始），用index表示数组里面每个元素的下标(下标从0开始)
(3)当有人需要出圈的时候，就把array[index%n]置为-1，然后整个队列的长度len减1
(4)最后当队列长度为1时，遍历这个队列，返回值不为1的位置即可
 * */
public class JosephCircle {



    public static int pickup(int n, int m) {
        //用数组模拟人组成的循环队列
        int[] array = new int[n];
        //初始化队列中的数据（人）
        for(int i = 0;i < n;i ++) {
            array[i] = i + 1;
        }

        //当前队列的长度
        int len = n;
        //当前报数，从1开始
        int currentCallNum = 1;

        int index = 0;  //数组下标，从0开始

        while(len != 1) {
            //判断当前位置是否有人(用取余数的方式实现对数组的循环遍历)
            if(array[index % n] != -1) {
                //判断当前报数是否为3
                if(currentCallNum % m == 0) {
                    array[index % n] = -1;
                    len --;
                }
                //报数+1
                currentCallNum ++;
            }
            //数组下标+1
            index ++;
        }

        //当队列长度为1时，遍历队列，获取值不为-1的位置的索引
        int lastPosition = 0;
        for(int i = 0;i < n;i ++) {
            if(array[i] != -1) {
                lastPosition = i;
            }
        }
        return lastPosition;
    }

    public static void main(String[] args) {

        System.out.println("最后留下的人原来排在：" + pickup(11, 3) + " 号！");
    }
}
