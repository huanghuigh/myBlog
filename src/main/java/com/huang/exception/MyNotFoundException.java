package com.huang.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 自定义异常
 * 返回状态码为未找到
 * @author guangtou
 * @create 2020--02--05--17:03
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyNotFoundException extends RuntimeException{

    public MyNotFoundException() {
        super();
    }

    public MyNotFoundException(String message) {
        super(message);
    }

    public MyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
