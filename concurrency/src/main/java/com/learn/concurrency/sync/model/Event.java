package com.learn.concurrency.sync.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 事件.
 * @author mac
 * */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {

    private String actorId;     //触发人
    private Integer entityId;   //实体ID
    private String entityType;  //实体类型
    private EventType eventType;//事件类型
    //扩展字段
    private Map<String, Object> exts = new HashMap<>();

    public void setExt(String key, Object value) {
        this.exts.put(key,value);
    }
    public Object getExt(String key) {
        return this.exts.get(key);
    }
}
