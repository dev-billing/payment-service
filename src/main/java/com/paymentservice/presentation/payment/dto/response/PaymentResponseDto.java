package com.paymentservice.presentation.payment.dto.response;


import com.paymentservice.domain.payment.enums.PaymentMethod;
import com.paymentservice.domain.payment.enums.PaymentStatus;
import com.paymentservice.domain.payment.model.Payment;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponseDto(
        UUID paymentId,
        Long reservationId,
        int amount,
        PaymentStatus status,
        PaymentMethod method,
        String pgTransactionId,
        LocalDateTime completeDateTime
) {
    public static PaymentResponseDto from(Payment payment) {
        return new PaymentResponseDto(payment.getPaymentId(),
                payment.getReservationId(),
                payment.getAmount().getValue(),
                payment.getStatus(),
                payment.getMethod(),
                payment.getPgTransactionId(),
                payment.getCompletedDateTime());
    }
}
