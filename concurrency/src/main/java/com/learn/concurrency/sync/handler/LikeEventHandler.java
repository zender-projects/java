package com.learn.concurrency.sync.handler;

import com.learn.concurrency.sync.model.Event;
import com.learn.concurrency.sync.model.EventType;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LikeEventHandler implements EventHandler{
    @Override
    public void doHandle(Event event) {
        try{
            log.info("Handling Event, {}", event);
            TimeUnit.SECONDS.sleep(2);
            log.info("Handle Event Completed, {}", event);
        }catch (Exception ex) {
            log.error("Handle Event Exception: {}", ex);
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.CREATE);
    }
}
