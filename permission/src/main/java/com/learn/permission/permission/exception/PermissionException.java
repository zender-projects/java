package com.learn.permission.permission.exception;

public class PermissionException extends RuntimeException{

    public PermissionException(){}

    public PermissionException(String msg) { super(msg); }

    public PermissionException(Throwable ex) { super(ex);  }
}
