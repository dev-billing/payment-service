package com.paymentservice.domain.payment.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {

    CARD("신용/체크카드"),
    ACCOUNT("계좌이체"),
    MOBILE("휴대폰 결제");

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }
}
