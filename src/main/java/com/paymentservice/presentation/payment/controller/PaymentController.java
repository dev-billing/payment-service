package com.paymentservice.presentation.payment.controller;

import com.paymentservice.application.payment.service.PaymentApplicationService;
import com.paymentservice.presentation.common.response.ApiResponse;
import com.paymentservice.presentation.payment.dto.request.PaymentRequestDto;
import com.paymentservice.presentation.payment.dto.response.PaymentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentApplicationService paymentApplicationService;

    @PostMapping
    public ApiResponse<PaymentResponseDto> payment(@RequestBody PaymentRequestDto paymentRequestDto) {
        return ApiResponse.ok(paymentApplicationService.payment(paymentRequestDto));
    }
}
