package com.roy.order.base.enums.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    // @formatter:off
    // ACCOUNT

    // PRODUCT
    NOT_EXIST_KIT(20001, "존재하지 않는 키트입니다."),
    NOT_EXIST_CLASS(20002, "존재하지 않는 클래스입니다."),

    // COMMON
    NOT_YET_DEVELOPED(30001, "아직 개발되지 않은 기능입니다."),
    INVALID_INPUT(30002, "무효한 값을 입력하였습니다."),
    INVALID_PRODUCT_NUMBER_INPUT(30003, "무효한 상품 번호를 입력하였습니다."),
    INVALID_PRODUCT_COUNT_INPUT(30004, "재품 수량은 양의 정수만 가능합니다."),
    NOT_ENOUGH_STOCK_COUNT(30004, "재고의 수량이 부족합니다."),
    DUPLICATED_KLASS_ORDER(30005, "장바구니에 중복된 클래스가 존재합니다."),
    INTERNAL_SERVICE_EXCEPTION(30006, "서비스 내부에 오류가 발생하였습니다. 관리자에게 문의해주세요.");
    // @formatter:on

    int value;

    String message;

}
