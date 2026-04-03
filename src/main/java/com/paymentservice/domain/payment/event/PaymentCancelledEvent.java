package com.paymentservice.domain.payment.event;

import com.paymentservice.infra.event.DomainEvent;

public record PaymentCancelledEvent(
        long reservationId) implements DomainEvent {
}
