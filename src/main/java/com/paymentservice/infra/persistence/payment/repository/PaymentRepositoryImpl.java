package com.paymentservice.infra.persistence.payment.repository;


import com.paymentservice.domain.payment.model.Payment;
import com.paymentservice.domain.payment.repository.PaymentRepository;
import com.paymentservice.infra.persistence.payment.entity.PaymentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Optional<Payment> findByReservationId(long reservationId) {
        return paymentJpaRepository.findByReservationId(reservationId)
                .map(PaymentEntity::toDomain);
    }

    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(PaymentEntity.from(payment))
                .toDomain();
    }

    @Override
    public void update(Payment payment) {
        paymentJpaRepository.save(PaymentEntity.from(payment));
    }
}
