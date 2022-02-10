package com.roy.order.controllers;

import com.roy.order.base.enums.exception.ExceptionCode;
import com.roy.order.base.exceptions.InvalidStatusException;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
@Profile("!test")
public class AdminController extends AbstractController {

    @Override
    public void run(String... args) throws Exception {
        throw new InvalidStatusException(ExceptionCode.NOT_YET_DEVELOPED);
    }
}
