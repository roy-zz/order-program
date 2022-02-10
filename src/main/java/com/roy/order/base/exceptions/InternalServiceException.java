package com.roy.order.base.exceptions;

import com.roy.order.base.enums.exception.ExceptionCode;

public class InternalServiceException extends AbstractWarnException {

    public InternalServiceException(ExceptionCode code) {
        super(code);
    }
}
