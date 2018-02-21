package com.learn.permission.common.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class JsonDataResult {

    private boolean ret;
    private String msg;
    private Object data;

    public JsonDataResult(boolean ret){
        this.ret = ret;
    }

    public static JsonDataResult success(Object data, String msg) {
        JsonDataResult jsonData = new JsonDataResult(true);
        jsonData.msg = msg;
        jsonData.data = data;

        return jsonData;
    }

    public static JsonDataResult success(Object data) {
        JsonDataResult jsonData = new JsonDataResult(true);
        jsonData.data = data;

        return jsonData;
    }

    public static JsonDataResult success(){
        JsonDataResult jsonData = new JsonDataResult(true);
        jsonData.data = "success";

        return jsonData;
    }

    public static JsonDataResult fail(String msg){
        JsonDataResult jsonData = new JsonDataResult(false);
        jsonData.msg = msg;
        return  jsonData;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("ret", this.ret);
        result.put("msg", this.msg);
        result.put("data", this.data);
        return result;
    }
}
