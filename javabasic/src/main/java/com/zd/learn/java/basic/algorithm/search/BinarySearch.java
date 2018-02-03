package com.zd.learn.java.basic.algorithm.search;

/**
 * 二分查找.
 * @author mac
 * */
public class BinarySearch {

    /**
     * @param data 原始数据
     * @param target 查找对象
     * @param from 二分边界
     * @param  to 二分边界
     *            @return  目标索引位置
     * */
    public static int binarySearch(int[] data, int target, int from, int to) {

       if( from < to ) {
           int mid = (from + to ) / 2;

           if(data[mid] == target) {
               return mid;
           }else if(data[mid] > target) {
               binarySearch(data, target, from, mid - 1);
           }else {
               binarySearch(data, target, mid + 1, to);
           }
       }
       return  - 1;
    }

    public static void main(String[] args) {

        int[] array = {1,5,3,76,9,33,25,6};
        int index = binarySearch(array, 76, 0, array.length - 1);
        System.out.println(index);
    }

}
