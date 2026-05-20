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

    /**
     * 결제 처리
     * @apiScope external
     *
     * 전달받은 결제 요청을 처리하고 결제 결과를 반환합니다.
     * 결제 실패 시 사유 코드와 함께 오류 응답이 반환됩니다.
     *
     * @return 결제 처리 결과 (결제 ID, 승인 시각, 승인 번호 등)
     */
    @PostMapping
    public ApiResponse<PaymentResponseDto> payment(@RequestBody PaymentRequestDto paymentRequestDto) {
        return ApiResponse.ok(paymentApplicationService.payment(paymentRequestDto));
    }
}
