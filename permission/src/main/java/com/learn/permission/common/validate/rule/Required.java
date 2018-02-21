package com.learn.permission.common.validate.rule;

import com.learn.permission.common.exception.ParameterException;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class Required implements Rule{

    @Override
    public void validate(Object value, String field) {
        if(Objects.isNull(value)) {
            throw new ParameterException("参数" + field + "为空");
        }

        if(value instanceof String) {
            String tempVal = (String)value;
            if("".equals(tempVal)) {
                throw new ParameterException("参数" + field + "为空");
            }
        }

        if(value instanceof Collection<?>) {
            Collection<?> tempVal = (Collection<?>)value;
            if(tempVal.size() == 0) {
                throw new ParameterException("参数" + field + "为空");
            }
        }

        if(value instanceof Map<?,?>) {
            Map<?,?> tempVal = (Map<?,?>)value;
            if(tempVal.size() == 0) {
                throw new ParameterException("参数" + field + "为空");
            }
        }
    }
}
