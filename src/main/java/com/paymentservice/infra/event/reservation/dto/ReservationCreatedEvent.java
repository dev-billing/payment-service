package com.paymentservice.infra.event.reservation.dto;

import com.paymentservice.infra.event.DomainEvent;

public record ReservationCreatedEvent(
        long reservationId,
        int amount
) implements DomainEvent {
}
