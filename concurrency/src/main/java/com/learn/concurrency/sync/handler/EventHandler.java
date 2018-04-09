package com.learn.concurrency.sync.handler;

import com.learn.concurrency.sync.model.Event;
import com.learn.concurrency.sync.model.EventType;

import java.util.List;

/**
 * Handler接口.
 * @author mac
 * */
public interface EventHandler {

    /**
     * 处理事件.
     * @param event
     * */
    void doHandle(Event event);

    /**
     * 获取事件处理器能够处理的事件类型.
     * @return List<EventType>
     * */
    List<EventType> getSupportEventTypes();
}
