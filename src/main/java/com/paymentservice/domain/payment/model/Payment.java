package com.paymentservice.domain.payment.model;

import com.paymentservice.domain.common.model.Money;
import com.paymentservice.domain.payment.enums.PaymentMethod;
import com.paymentservice.domain.payment.enums.PaymentStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Payment {

    private final UUID paymentId;

    private final Long reservationId;

    private final Money amount;

    private PaymentStatus status;

    private PaymentMethod method;

    private String pgTransactionId;

    private LocalDateTime completedDateTime;

    @Builder(access = AccessLevel.PRIVATE)
    private Payment(UUID paymentId, Long reservationId, Money amount, PaymentStatus status, PaymentMethod method, String pgTransactionId, LocalDateTime completedDateTime) {
        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.amount = amount;
        this.status = status;
        this.method = method;
        this.pgTransactionId = pgTransactionId;
        this.completedDateTime = completedDateTime;
    }

    public static Payment create(UUID paymentId, Long reservationId, int amount, PaymentStatus status, PaymentMethod method, String pgTransactionId, LocalDateTime completedDateTime) {
        return Payment.builder()
                .paymentId(paymentId)
                .reservationId(reservationId)
                .amount(Money.create(amount))
                .status(status)
                .method(method)
                .pgTransactionId(pgTransactionId)
                .completedDateTime(completedDateTime)
                .build();
    }

    public static Payment create(Long reservationId, int amount) {
        return Payment.builder()
                .reservationId(reservationId)
                .amount(Money.create(amount))
                .status(PaymentStatus.PENDING)
                .build();
    }

    public void complete(String pgTransactionId, LocalDateTime completedDateTime) {
        if (this.status != PaymentStatus.PENDING) {
            throw new IllegalArgumentException("결제 대기 상태만 결제 완료처리가 가능합니다.");
        }
        this.pgTransactionId = pgTransactionId;
        this.completedDateTime = completedDateTime;
        this.status = PaymentStatus.COMPLETED;
    }

    public void cancel() {
        if (this.status == PaymentStatus.PENDING || this.status == PaymentStatus.COMPLETED) {
            this.status = PaymentStatus.CANCELLED;
            return ;
        }
        throw new IllegalArgumentException("예약을 취소하려면 완료된 결제만 가능합니다.");
    }

    public void changePaymentMethod(PaymentMethod method) {
        this.method = method;
    }

}
