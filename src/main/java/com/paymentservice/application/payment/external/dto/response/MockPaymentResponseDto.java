package com.paymentservice.application.payment.external.dto.response;

public record MockPaymentResponseDto(
        boolean success,
        String pgTransactionId,
        String failureReason

) {

    public static MockPaymentResponseDto success(String pgTransactionId) {
        return new MockPaymentResponseDto(true, pgTransactionId, null);
    }

    public static MockPaymentResponseDto failure(String failureReason) {
        return new MockPaymentResponseDto(false, null, failureReason);
    }
}
