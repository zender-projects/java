package com.learn.permission.common.validate;

import com.learn.permission.common.validate.rule.Rule;

public class Validator {

    public static void validate(Object value, String filed, Rule... rules){
        for(Rule rule : rules) {
            rule.validate(value, filed);
        }
    }
}
