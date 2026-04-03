package com.paymentservice.infra.persistence.payment.repository;

import com.paymentservice.infra.persistence.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, UUID> {

    Optional<PaymentEntity> findByReservationId(long reservationId);

}
