package com.roy.order.base.exceptions;

import com.roy.order.base.enums.exception.ExceptionCode;

public class SoldOutException extends AbstractWarnException {

    public SoldOutException(ExceptionCode code) {
        super(code);
    }
}
