package com.paymentservice.infra.event.reservation.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentservice.infra.event.EventPublisher;
import com.paymentservice.infra.event.reservation.dto.PaymentCreatedFailEvent;
import com.paymentservice.infra.event.reservation.dto.ReservationCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationDlqEventListener {

    private final ObjectMapper objectMapper;
    private final EventPublisher eventPublisher;

    @KafkaListener(topics = "reservation-created.dlq", groupId = "movie-reservation")
    public void handleCreatedReservationDlq(String message, Acknowledgment ack) {
        log.info("handleCreatedReservationDlq : {}", message);
        try {

            ReservationCreatedEvent event =
                    objectMapper.readValue(message, ReservationCreatedEvent.class);

            eventPublisher.publish("payment-created-fail", new PaymentCreatedFailEvent(event.reservationId()));
            ack.acknowledge();
        } catch (JsonProcessingException e) {
            log.error("역직렬화 실패 - Topic: reservation-created.dlq : {}", message, e);
            throw new IllegalArgumentException("메시지 역직렬화 실패 : " + message);
        }
    }

}
