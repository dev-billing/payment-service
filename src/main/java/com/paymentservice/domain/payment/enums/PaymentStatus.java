package com.paymentservice.domain.payment.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {

    PENDING("결제 대기"),
    COMPLETED("결제 완료"),
    CANCELLED("결제 취소");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

}
