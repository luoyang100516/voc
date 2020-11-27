package com.lizhen.common.exceptions;

/**
 * 登录超时异常
 */
public class LoginTimeOutException extends RuntimeException {

    public LoginTimeOutException(String msg){
        super(msg);
    }

    public LoginTimeOutException() {
        super();
    }
}
