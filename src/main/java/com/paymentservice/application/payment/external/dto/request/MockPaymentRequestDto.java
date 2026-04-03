package com.paymentservice.application.payment.external.dto.request;

import com.paymentservice.domain.payment.enums.PaymentMethod;

public record MockPaymentRequestDto(
        long reservationId,
        PaymentMethod paymentMethod
) {
}
