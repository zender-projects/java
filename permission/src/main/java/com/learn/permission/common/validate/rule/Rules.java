package com.learn.permission.common.validate.rule;

public class Rules{

    public static Rule REQUIRED() {  return new Required(); }

    public static Rule MAX(Integer maxLength) { return new Max(maxLength); }

    public static Rule MIN(Integer minLength) { return new Min(minLength); }
}
