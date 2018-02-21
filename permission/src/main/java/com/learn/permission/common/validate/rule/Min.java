package com.learn.permission.common.validate.rule;

import com.learn.permission.common.exception.ParameterException;

import java.util.Collection;
import java.util.Map;

public class Min implements Rule{

    private Integer minLength;
    public Min(Integer minLength) {
        this.minLength = minLength;
    }

    @Override
    public void validate(Object value, String field) {
        if(value instanceof String) {
            String tempVal = (String)value;
            if(tempVal.length() > minLength) {
                throw new ParameterException("参数" + field + "过短,最小长度:" + minLength +",参数值:" + value);
            }
        }

        if(value instanceof Collection<?>) {
            Collection<?> tempVal = (Collection<?>)value;
            if(tempVal.size() > minLength) {
                throw new ParameterException("参数" + field + "过短,最小长度:" + minLength +",参数值:" + value);
            }
        }

        if(value instanceof Map<?,?>) {
            Map<?,?> tempVal = (Map<?,?>)value;
            if(tempVal.size() > minLength) {
                throw new ParameterException("参数" + field + "过短,最小长度:" + minLength +",参数值:" + value);
            }
        }
    }
}
