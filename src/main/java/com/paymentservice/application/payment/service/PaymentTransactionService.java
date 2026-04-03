package com.paymentservice.application.payment.service;

import com.paymentservice.domain.payment.event.PaymentCancelledEvent;
import com.paymentservice.domain.payment.event.PaymentCompletedEvent;
import com.paymentservice.domain.payment.model.Payment;
import com.paymentservice.domain.payment.repository.PaymentRepository;
import com.paymentservice.infra.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentTransactionService {

    private final PaymentRepository paymentRepository;
    private final EventPublisher eventPublisher;

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment completedPayment(Payment payment, String pgTransactionId) {
        payment.complete(pgTransactionId, LocalDateTime.now());
        Payment updatePayment = this.update(payment);
        eventPublisher.publish("payment-completed", new PaymentCompletedEvent(payment.getReservationId()));
        return updatePayment;
    }

    public void cancelPayment(Payment payment) {
        payment.cancel();
        this.update(payment);
        eventPublisher.publish("payment-cancelled", new PaymentCancelledEvent(payment.getReservationId()));
    }

    public Payment update(Payment payment) {
        return paymentRepository.save(payment);
    }
}
