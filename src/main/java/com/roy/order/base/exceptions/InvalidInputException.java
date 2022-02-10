package com.roy.order.base.exceptions;

import com.roy.order.base.enums.exception.ExceptionCode;

public class InvalidInputException extends AbstractInfoException {

    public InvalidInputException(ExceptionCode code) {
        super(code);
    }
}
