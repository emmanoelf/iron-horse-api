package com.ironhorse.client;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Component;

@Component
public class StripeClient {
    public Session createSession(SessionCreateParams params) throws StripeException {
        return Session.create(params);
    }
}
