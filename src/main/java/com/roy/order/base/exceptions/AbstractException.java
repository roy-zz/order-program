package com.roy.order.base.exceptions;

import com.roy.order.base.enums.exception.ExceptionCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractException extends RuntimeException {

    ExceptionCode code;

    public AbstractException(ExceptionCode code) {
        this.code = code;
    }
}
