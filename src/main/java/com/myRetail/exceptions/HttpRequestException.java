package com.myRetail.exceptions;

public class HttpRequestException extends Exception {
    public HttpRequestException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
