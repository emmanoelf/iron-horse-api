package com.ironhorse.service.impl;

import com.ironhorse.dto.PaymentDto;
import com.ironhorse.dto.PaymentResponseDto;
import com.ironhorse.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeServiceImpl implements PaymentService {
    private static final String CURRENCY_TYPE = "BRL";

    public PaymentResponseDto createPaymentLink(PaymentDto paymentDto) throws StripeException {
        long expiresAt = System.currentTimeMillis() / 1000 + 30 * 60;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/")
                .setCancelUrl("https://www.google.com")
                .setClientReferenceId(paymentDto.id().toString())
                .setExpiresAt(expiresAt)
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency(CURRENCY_TYPE)
                                .setUnitAmount(paymentDto.totalPrice() * 100)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName(paymentDto.name())
                                        .setDescription(paymentDto.description())
                                        //.addImage(paymentDto.image())
                                        .build())
                                .build())
                        .build())
                .setPaymentIntentData(SessionCreateParams.PaymentIntentData.builder()
                        .putMetadata("clientReferenceId", paymentDto.id().toString()).build()
                )
                .build();

        Session session = Session.create(params);
        PaymentResponseDto paymentResponseDto = new PaymentResponseDto(session.getUrl());

        return paymentResponseDto;
    }
}
