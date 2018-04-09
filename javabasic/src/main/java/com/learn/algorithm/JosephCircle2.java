package com.learn.algorithm;

public class JosephCircle2 {
    public static int pickup(int n, int m) {
        //初始化循环队列
        int[] array = new int[n];
        for(int i = 0;i < n;i ++) {
            array[i] = i + 1;
        }
        //当前队列长度
        int len = n;
        //当前报数
        int currentCallNum = 1;
        //当前索引
        int index = 0;

        //队列的长度=1时，停止遍历
        while(len > 1) {
            //判断当前位置是否有人
            if(array[index % n] != -1) {
                //判断当前报数是否为3
                if(currentCallNum % 3 == 0) {
                    //移除当前人，长度-1
                    array[index] = -1;
                    len --;
                }
                //如果当前位置有人，则报数+1
                currentCallNum ++;
            }
            //遍历到下一个人
            index ++;

        }
        //找到没有被移除的人的位置
        int lastPosition = 0;
        for(int i = 0;i < n;i ++) {
            if(array[i] != -1) {
                lastPosition = i;
            }
        }
        return lastPosition;
    }
}
