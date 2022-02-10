package com.roy.order.base.enums.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ProductType {

    // @formatter:off
    KLASS(0),
    KIT(1);
    // @formatter:on

    private final int value;

    public static ProductType valueOf(int value) {
        return Arrays.stream(values()).filter(i -> i.value == value).findFirst().orElse(null);
    }

}
