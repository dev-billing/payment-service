package com.paymentservice.application.payment.service;

import com.paymentservice.application.payment.external.ExternalPaymentService;
import com.paymentservice.application.payment.external.dto.response.MockPaymentResponseDto;
import com.paymentservice.domain.payment.enums.PaymentStatus;
import com.paymentservice.domain.payment.model.Payment;
import com.paymentservice.domain.payment.repository.PaymentRepository;
import com.paymentservice.infra.event.reservation.dto.ReservationCancelledEvent;
import com.paymentservice.infra.event.reservation.dto.ReservationCreatedEvent;
import com.paymentservice.presentation.payment.dto.request.PaymentRequestDto;
import com.paymentservice.presentation.payment.dto.response.PaymentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentApplicationService {

    private final ExternalPaymentService externalPaymentService;
    private final PaymentTransactionService paymentTransactionService;
    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentResponseDto create(ReservationCreatedEvent event) {
        if (paymentRepository.findByReservationId(event.reservationId()).isPresent()) {
            throw new IllegalArgumentException("해당 예약의 결제 정보가 존재합니다.");
        }
        Payment payment = Payment.create(event.reservationId(), event.amount());
        Payment createdPayment = paymentTransactionService.createPayment(payment);
        return PaymentResponseDto.from(createdPayment);
    }

    public PaymentResponseDto payment(PaymentRequestDto request) {
        Optional<Payment> optionalPayment = paymentRepository.findByReservationId(request.reservationId());

        if (optionalPayment.isEmpty()) {
            throw new IllegalArgumentException("취소할 결제를 찾지 못했습니다.");
        }

        Payment payment = optionalPayment.get();
        payment.changePaymentMethod(request.paymentMethod());
        MockPaymentResponseDto pgResponse = externalPaymentService.process(payment);

        if (!pgResponse.success()) {
            throw new IllegalArgumentException("결제를 실패했습니다.");
        }

        Payment completedPayment = paymentTransactionService.completedPayment(payment, pgResponse.pgTransactionId());
        return PaymentResponseDto.from(completedPayment);
    }

    public void cancel(ReservationCancelledEvent event) {
        Optional<Payment> optionalCancelPayment = paymentRepository.findByReservationId(event.reservationId());

        if (optionalCancelPayment.isEmpty()) {
            throw new IllegalArgumentException("취소할 결제를 찾지 못했습니다.");
        }

        Payment cancelPayment = optionalCancelPayment.get();
        if (cancelPayment.getStatus() == PaymentStatus.COMPLETED) {
            boolean result = externalPaymentService.cancel(cancelPayment.getPgTransactionId());
            if (!result) {
                throw new IllegalArgumentException("결제 취소 실패!");
            }
        }
        paymentTransactionService.cancelPayment(cancelPayment);
    }
}
