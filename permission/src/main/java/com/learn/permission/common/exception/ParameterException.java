package com.learn.permission.common.exception;

public class ParameterException extends RuntimeException{

    public ParameterException(){}

    public ParameterException(String msg){
        super(msg);
    }

    public ParameterException(Throwable ex) {
        super(ex);
    }
}
