package com.paymentservice.domain.payment.event;

import com.paymentservice.infra.event.DomainEvent;

public record PaymentCompletedEvent(
        long reservationId) implements DomainEvent {
}
