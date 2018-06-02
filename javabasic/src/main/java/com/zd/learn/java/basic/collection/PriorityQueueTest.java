package com.zd.learn.java.basic.collection;

import java.time.LocalDate;
import java.util.PriorityQueue;

public class PriorityQueueTest {

    public static void main(String[] args) {

        PriorityQueue<LocalDate> queue = new PriorityQueue();

        queue.add(LocalDate.of(1906,12,9));
        queue.add(LocalDate.of(1815, 12, 10));
        queue.add(LocalDate.of(1903, 12,3));
        queue.add(LocalDate.of(1910, 6,22));


        System.out.println("iterating over elements... ");
        for (LocalDate date : queue) {
            System.out.println(date);
        }

        System.out.println("removing elements...");
        while (!queue.isEmpty())
            System.out.println(queue.remove());
    }

}
