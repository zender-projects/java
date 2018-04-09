package com.learn.concurrency.sync.producer;

import com.learn.concurrency.sync.model.Event;
import com.learn.concurrency.sync.queue.EventBlockingQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventProducer {

    @Autowired
    private EventBlockingQueue<Event> eventBlockingQueue;

    /**
     * 向队列发送事件.
     * @param event
     * @return boolean
     *          true  - 发送成功
     *          false - 发送失败
     * */
    public boolean fireEvent(Event event) {
        try{
            return eventBlockingQueue.offer(event);
        }catch (Exception ex) {
            log.error("发送事件异常, {}", ex.getMessage());
        }
        return false;
    }

}
