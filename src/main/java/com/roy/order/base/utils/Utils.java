package com.roy.order.base.utils;

import com.roy.order.base.enums.exception.ExceptionCode;
import com.roy.order.base.exceptions.InvalidInputException;

import java.text.NumberFormat;
import java.util.Locale;

public class Utils {

    public static int stringToIntegerAndCheckPositiveInteger(String number) {
        try {
            int inputInteger = Integer.parseInt(number);

            if (inputInteger > 0) {
                return inputInteger;
            }
            else {
                throw new InvalidInputException(ExceptionCode.INVALID_PRODUCT_COUNT_INPUT);
            }
        } catch (NumberFormatException e) {
            throw new InvalidInputException(ExceptionCode.INVALID_PRODUCT_NUMBER_INPUT);
        }
    }

    public static String transNumberToDefaultPriceFormat(Number source) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.KOREA);
        return format.format(source).replace("₩", "");
    }
}
