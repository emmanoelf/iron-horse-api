package com.ironhorse.config.gatewayPayment;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    @Value("${api.key.stripe-secret}")
    private String secretApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretApiKey;
    }

}
