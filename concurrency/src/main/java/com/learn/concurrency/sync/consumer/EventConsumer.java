package com.learn.concurrency.sync.consumer;

import com.learn.concurrency.sync.handler.EventHandler;
import com.learn.concurrency.sync.model.Event;
import com.learn.concurrency.sync.model.EventType;
import com.learn.concurrency.sync.queue.EventBlockingQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class EventConsumer implements InitializingBean, DisposableBean, ApplicationContextAware {

    private static final Integer CONSUMER_NUM = 10;
    private ApplicationContext applicationContext;
    private ExecutorService executorService;
    @Autowired
    private EventBlockingQueue<Event> eventBlockingQueue;

    private Map<EventType, List<EventHandler>> handlerMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化消费者线程池
        executorService = Executors.newFixedThreadPool(CONSUMER_NUM);

        //初始化系统中的EventHandler
        Map<String, EventHandler> eventHandlerMap = applicationContext.getBeansOfType(EventHandler.class);

        if(!Objects.isNull(eventHandlerMap)) {
            for(Map.Entry<String, EventHandler> entry : eventHandlerMap.entrySet()) {
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();
                if(!Objects.isNull(eventTypes)) {
                    for(EventType type : eventTypes) {
                        if(!handlerMap.containsKey(type)) {
                            handlerMap.put(type, new ArrayList<EventHandler>());
                        }
                        handlerMap.get(type).add(entry.getValue());
                    }
                }
            }
        }

        //开启处理线程
        executorService.execute(() -> {
            try{
                while (!Thread.interrupted()) {
                    Event event = eventBlockingQueue.take();
                    if (!handlerMap.containsKey(event.getEventType())) {
                        log.error("Unknow Event Type, {}", event);
                        continue;
                    }
                    //循环遍历Hnadler，处理事件
                    for(EventHandler handler : handlerMap.get(event.getEventType())) {
                        handler.doHandle(event);
                    }
                }
            }catch (Exception ex) {
                log.error("获取Event异常,{}", ex.getMessage());
            }
        });
    }

    @Override
    public void destroy() throws Exception {
        if(!Objects.isNull(executorService)) {
            executorService.shutdown();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
