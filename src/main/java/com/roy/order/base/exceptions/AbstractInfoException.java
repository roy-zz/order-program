package com.roy.order.base.exceptions;

import com.roy.order.base.enums.exception.ExceptionCode;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public abstract class AbstractInfoException extends AbstractException {

    public AbstractInfoException(ExceptionCode code) {
        super(code);
    }
}
