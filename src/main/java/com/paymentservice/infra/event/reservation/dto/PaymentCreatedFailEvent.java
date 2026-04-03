package com.paymentservice.infra.event.reservation.dto;

import com.paymentservice.infra.event.DomainEvent;

public record PaymentCreatedFailEvent(
        long reservationId
) implements DomainEvent {
}
