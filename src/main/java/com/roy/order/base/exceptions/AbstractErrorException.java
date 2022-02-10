package com.roy.order.base.exceptions;

import com.roy.order.base.enums.exception.ExceptionCode;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public abstract class AbstractErrorException extends AbstractException {

    public AbstractErrorException(ExceptionCode code) {
        super(code);
    }
}
