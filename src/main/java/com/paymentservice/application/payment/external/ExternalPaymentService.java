package com.paymentservice.application.payment.external;

import com.paymentservice.application.payment.external.dto.response.MockPaymentResponseDto;
import com.paymentservice.domain.payment.model.Payment;

public interface ExternalPaymentService {

    MockPaymentResponseDto process(Payment payment);

    boolean cancel(String pgTransactionId);
}
