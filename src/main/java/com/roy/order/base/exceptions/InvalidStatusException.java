package com.roy.order.base.exceptions;

import com.roy.order.base.enums.exception.ExceptionCode;

public class InvalidStatusException extends AbstractWarnException {

    public InvalidStatusException(ExceptionCode code) {
        super(code);
    }
}
