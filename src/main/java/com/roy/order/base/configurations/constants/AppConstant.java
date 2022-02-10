package com.roy.order.base.configurations.constants;

public interface AppConstant {

    interface Command {
        String QUIT = "q";
        String ADMIN = "a";
        String ORDER = "o";
        String PAYMENT = " ";
    }

    interface InformationMessage {
        String THANKS_FOR_ORDER = "고객님의 주문 감사합니다.";
        String UNEXPECTED_EXCEPTION = "오류가 발생하였습니다. 관리자에게 문의해주세요";
    }

    interface Product {
        int PRODUCT_TOTAL_PRICE_FOR_FREE_DELIVERY_CHARGE = 50000;
        int BASIC_DELIVERY_CHARGE = 5000;
    }

    interface Common {
        String LINE_SPLIT = "------------------------------------------------------------";
    }
}
