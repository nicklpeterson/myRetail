package com.myRetail.exceptions;

public class ProductServiceException extends Exception {
    public ProductServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
