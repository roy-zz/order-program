package com.roy.order.base.exceptions;

import com.roy.order.base.enums.exception.ExceptionCode;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public abstract class AbstractWarnException extends AbstractException {

    public AbstractWarnException(ExceptionCode code) {
        super(code);
    }
}
