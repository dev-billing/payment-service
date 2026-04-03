package com.paymentservice.presentation.payment.dto.request;


import com.paymentservice.domain.payment.enums.PaymentMethod;

public record PaymentRequestDto(
        long reservationId,
        int amount,
        PaymentMethod paymentMethod
) {
}
