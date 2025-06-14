package com.ironhorse.service;

import com.ironhorse.client.StripeClient;
import com.ironhorse.dto.PaymentDto;
import com.ironhorse.dto.PaymentResponseDto;
import com.ironhorse.service.impl.StripeServiceImpl;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StripePaymentServiceTest {

    @Mock
    private StripeClient stripeClient;

    @InjectMocks
    private StripeServiceImpl stripeService;

    @Test
    @DisplayName("Should be able to create a payment link successfully")
    public void shouldCreatePaymentLinkSuccessfully() throws StripeException {
        PaymentDto paymentDto = new PaymentDto(1L, "Datsun 240Z", 1L, 500L, "Um dos carros mais bonitos já feito", "http://path-to-image.com");

        Session fakeSession = new Session();
        fakeSession.setUrl("http://stripe.com/generate-link-checkout");
        when(this.stripeClient.createSession(any(SessionCreateParams.class))).thenReturn(fakeSession);

        PaymentResponseDto response = this.stripeService.createPaymentLink(paymentDto);
        assertEquals("http://stripe.com/generate-link-checkout", response.url());
    }
}
