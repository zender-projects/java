package com.learn.permission.common.validate.rule;

import com.learn.permission.common.exception.ParameterException;

import java.util.Collection;
import java.util.Map;

public class Max implements Rule{

    private Integer maxLength;
    public Max(Integer maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public void validate(Object value, String field) {

        if(value instanceof String) {
            String tempVal = (String)value;
            if(tempVal.length() > maxLength) {
                throw new ParameterException("参数" + field + "过长,最大长度:" + maxLength +",参数值:" + value);
            }
        }

        if(value instanceof Collection<?>) {
            Collection<?> tempVal = (Collection<?>)value;
            if(tempVal.size() > maxLength) {
                throw new ParameterException("参数" + field + "过长,最大长度:" + maxLength +",参数值:" + value);
            }
        }

        if(value instanceof Map<?,?>) {
            Map<?,?> tempVal = (Map<?,?>)value;
            if(tempVal.size() > maxLength) {
                throw new ParameterException("参数" + field + "过长,,最大长度:" + maxLength +",参数值:" + value);
            }
        }
    }
}
