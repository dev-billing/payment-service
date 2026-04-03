package com.paymentservice.infra.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public <T extends DomainEvent> void publish(String topic, T event) {
        try {
            kafkaTemplate.send(topic, event);
            log.info("이벤트 발행 완료 - Topic: {}, Event: {}", topic, event.getClass().getSimpleName());
        } catch (Exception e) {
            log.error("이벤트 발행 실패 - Topic: {}, Error: {}", topic, e.getMessage(), e);
            throw new RuntimeException("이벤트 발행 실패", e);
        }
    }
}
