package com.learn.permission.common.util;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;

@Slf4j
public class JsonMapperUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static  {
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }


    //对象转换成json串
    public static <T> String obj2String(T srcObj) {
        if(srcObj == null) {
            return null;
        }
        try{
            return srcObj instanceof String ? (String)srcObj : objectMapper.writeValueAsString(srcObj);
        }catch (Exception ex) {
            log.error("Json转换异常,error:[{}]", ex);
            return null;
        }
    }

    //Json串转换成对象
    public static <T> T string2obj(String src, TypeReference<T> typeReference){
        if(src == null || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? src : objectMapper.readValue(src, typeReference));
        }catch (Exception ex) {
            log.error("Json转换异常,error:[{},{},{}]", src,typeReference.getType(), ex);
            return null;
        }
    }

}
