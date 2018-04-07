package com.learn.concurrency.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//flag class for thread safe
//class
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE) //source, no complie
public @interface ThreadSafe {
    String value() default "";
}
