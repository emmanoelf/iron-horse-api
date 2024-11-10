package com.ironhorse.service;

import com.ironhorse.dto.PaymentDto;
import com.ironhorse.dto.PaymentResponseDto;
import com.stripe.exception.StripeException;

public interface PaymentService {
    PaymentResponseDto createPaymentLink(PaymentDto paymentDto) throws StripeException;
}
