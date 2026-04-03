package com.paymentservice.domain.payment.repository;

import com.paymentservice.domain.payment.model.Payment;

import java.util.Optional;

public interface PaymentRepository {

    Optional<Payment> findByReservationId(long reservationId);

    Payment save(Payment payment);

    void update(Payment payment);

}
