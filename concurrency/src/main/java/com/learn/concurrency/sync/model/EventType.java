package com.learn.concurrency.sync.model;

/**
 * 事件类型.
 * @author mac
 * */
public enum EventType {

    CREATE(0),
    SUBMIT(1),
    TERMINATE(2);

    private int value;
    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
