package com.paymentservice.infra.event.reservation.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentservice.application.payment.service.PaymentApplicationService;
import com.paymentservice.infra.event.reservation.dto.ReservationCancelledEvent;
import com.paymentservice.infra.event.reservation.dto.ReservationCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationEventListener {

    private final ObjectMapper objectMapper;
    private final PaymentApplicationService paymentApplicationService;

    @KafkaListener(topics = "reservation-created", groupId = "movie-reservation")
    public void handleCreatedReservation(String message, Acknowledgment ack) {
        log.info("handleCreatedReservation : {}", message);
        try {
            ReservationCreatedEvent event =
                    objectMapper.readValue(message, ReservationCreatedEvent.class);

            paymentApplicationService.create(event);
            ack.acknowledge();
        } catch (JsonProcessingException e) {
            log.error("역직렬화 실패 - Topic: reservation-created : {}", message, e);
            throw new IllegalArgumentException("메시지 역직렬화 실패 : " + message);
        }
    }

    @KafkaListener(topics = "reservation-cancelled", groupId = "movie-reservation")
    public void handleCancelReservation(String message, Acknowledgment ack) {
        log.info("Received reservation cancel message: {}", message);
        try {
            ReservationCancelledEvent event =
                    objectMapper.readValue(message, ReservationCancelledEvent.class);

            paymentApplicationService.cancel(event);
            ack.acknowledge();
        } catch (JsonProcessingException e) {
            log.error("역직렬화 실패 - Topic: reservation-cancelled : {}", message, e);
            throw new IllegalArgumentException("메시지 역직렬화 실패 : " + message);
        }
    }


}
