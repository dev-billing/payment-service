package com.paymentservice.infra.persistence.payment.entity;

import com.paymentservice.domain.payment.enums.PaymentMethod;
import com.paymentservice.domain.payment.enums.PaymentStatus;
import com.paymentservice.domain.payment.model.Payment;
import com.paymentservice.infra.persistence.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id")
    private UUID paymentId;

    @Column(nullable = false, unique = true)
    private Long reservationId; // 예매 ID

    private int amount; // 결제 금액

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status; // 결제 상태

    @Enumerated(EnumType.STRING)
    private PaymentMethod method; // 결제 수단

    private String pgTransactionId; // PG사 거래 ID

    private LocalDateTime completedDateTime;

    @Builder
    private PaymentEntity(UUID paymentId, Long reservationId, int amount, PaymentStatus status, PaymentMethod method, String pgTransactionId, LocalDateTime completedDateTime) {
        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.amount = amount;
        this.status = status;
        this.method = method;
        this.pgTransactionId = pgTransactionId;
        this.completedDateTime = completedDateTime;
    }

    public static PaymentEntity from(Payment payment) {
        return PaymentEntity.builder()
                .paymentId(payment.getPaymentId())
                .reservationId(payment.getReservationId())
                .amount(payment.getAmount().getValue())
                .status(payment.getStatus())
                .method(payment.getMethod())
                .pgTransactionId(payment.getPgTransactionId())
                .completedDateTime(payment.getCompletedDateTime())
                .build();
    }

    public Payment toDomain() {
        return Payment.create(paymentId, reservationId, amount, status, method, pgTransactionId, completedDateTime);
    }
}
