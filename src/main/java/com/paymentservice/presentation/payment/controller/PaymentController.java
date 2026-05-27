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
@RequestMapping("/internal/payments")
public class PaymentController {

    private final PaymentApplicationService paymentApplicationService;

    /**
     * 결제 처리
     * @apiScope external
     *
     * 전달받은 결제 요청을 처리하고 결제 결과를 반환합니다.
     * 결제 실패 시 사유 코드와 함께 오류 응답이 반환됩니다.
     *
     * 게이트웨이가 외부 요청 /api/payments 를 내부 /internal/payments 로 변환해 전달합니다.
     *
     * @body request 결제 요청 정보 (예약 ID, 금액, 결제 수단)
     * @return 결제 처리 결과 (결제 ID, 승인 시각, 승인 번호 등)
     */
    @PostMapping
    public ApiResponse<PaymentResponseDto> payment(@RequestBody PaymentRequestDto request) {
        return ApiResponse.ok(paymentApplicationService.payment(request));
    }
}
