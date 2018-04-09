package com.learn.concurrency.sync.queue;

import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * 阻塞队列.
 * @author mac
 * */
@Component
public class EventBlockingQueue<T> extends LinkedBlockingDeque<T>{ }
