package com.learn.permission.login.exception;

public class LoginException extends RuntimeException{

    public LoginException(){ }

    public LoginException(String msg) {
        super(msg);
    }

    public LoginException(Throwable ex) {
        super(ex);
    }

}
