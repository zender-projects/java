package com.zd.learn.java.basic.algorithm.sort;

/**
 * 冒泡排序.
 * @author mac
 * */
public class BubboSort {

    //数组为引用数据类型，可以进行引用传递，不需要返回值
    public static void bubboSort(int[] data) {

        for(int i = 0;i < data.length;i ++) {
            for(int j = 0;j < data.length - 1 - i;j ++) {

                if( data[j] > data[j + 1] ) {

                    int temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                }

            }
        }

    }

    public static void main(String[] args) {
        int[] array = {1,5,3,76,9,33,25,6};
        bubboSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(" " + array[i]);
        }
    }
}
