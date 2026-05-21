package com.paymentservice.presentation.payment.dto.request;


import com.paymentservice.domain.payment.enums.PaymentMethod;

public record PaymentRequestDto(
        /**
         * 결제할 예약의 고유 식별자.
         * @ex 1001
         */
        long reservationId,

        /**
         * 결제 금액 (원 단위).
         * @ex 50000
         */
        int amount,

        /**
         * 결제 수단. 가능한 값은 PaymentMethod enum 정의를 따른다.
         * @ex "CARD"
         */
        PaymentMethod paymentMethod
) {
}
