package com.myRetail.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,
        reason = "Request must contain currency and price in proper JSON.")
public class BadPriceUpdateRequest extends Exception {
}
