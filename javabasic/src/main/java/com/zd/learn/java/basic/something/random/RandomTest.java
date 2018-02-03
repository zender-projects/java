package com.zd.learn.java.basic.something.random;

import java.util.Arrays;
import java.util.Random;

public class RandomTest {


    public static void main(String[] args) {

        Random random = new Random();

        System.out.println(random.nextInt(100)); // 边界为100


        int[] arr = {1,2,3,4,5,6,7};

        int index = Arrays.binarySearch(arr, 5);
        System.out.println(index);

        int[] arr1 = new int[6];
        int[] arr2 = Arrays.copyOf(arr, 3);
        System.out.println(Arrays.toString(arr2));


        int[] arr3 = {1,2,3,4,5,6,7};
        System.out.println(Arrays.equals(arr, arr3));


    }
}
