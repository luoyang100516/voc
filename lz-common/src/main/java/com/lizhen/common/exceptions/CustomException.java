package com.lizhen.common.exceptions;

/**
 * 自定义异常(CustomException)
 */
public class CustomException extends RuntimeException {

    public CustomException(String msg){
        super(msg);
    }

    public CustomException() {
        super();
    }
}
