package com.paymentservice.application.payment.external;

import com.paymentservice.application.payment.external.dto.response.MockPaymentResponseDto;
import com.paymentservice.domain.payment.model.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
public class MockPaymentService implements ExternalPaymentService {

    private final Random random = new Random();

    @Override
    public MockPaymentResponseDto process(Payment payment) {
        log.info("Mock PG 결제 요청: paymentId={}, amount={}",
                payment.getPaymentId(),
                payment.getAmount().getValue());

        // 실제 PG 연동 시뮬레이션 (2초 대기)
        simulateNetworkDelay();

        // 90% 확률로 성공
        if (random.nextInt(10) < 9) {
            String pgTransactionId = "PG-" + UUID.randomUUID().toString();
            log.info("Mock PG 결제 승인: pgTransactionId={}", pgTransactionId);
            return MockPaymentResponseDto.success(pgTransactionId);
        } else {
            String failureReason = "카드 한도 초과";
            log.warn("Mock PG 결제 실패: reservationId : {}, reason={}", payment.getReservationId(), failureReason);
            return MockPaymentResponseDto.failure(failureReason);
        }
    }

    @Override
    public boolean cancel(String pgTransactionId) {
        log.info("Mock PG 결제 취소 요청: pgTransactionId={}", pgTransactionId);

        simulateNetworkDelay();

        // 95% 확률로 취소 성공
        boolean success = random.nextInt(100) < 95;
        log.info("Mock PG 결제 취소 결과: success={}", success);
        return success;
    }

    // 1 ~ 2초 대기
    private void simulateNetworkDelay() {
        try {
            Thread.sleep(1000 + random.nextInt(1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
