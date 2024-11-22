package com.ironhorse.service.impl;

import com.ironhorse.service.RentalService;
import com.ironhorse.service.WebhookService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("deprecation")
public class WebhookStripeServiceImpl implements WebhookService {
    @Value("${api.key.stripe-webhook}")
    private String endpointSecret;
    private final RentalService rentalService;

    public WebhookStripeServiceImpl(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @Override
    public String handleWebhook(String payload, String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

            switch (event.getType()) {
                case "checkout.session.completed":
                    this.handleCheckoutSessionCompleted((Session) event.getData().getObject());
                    break;
                case "checkout.session.expired":
                    this.handleCheckoutSessionExpired((Session) event.getData().getObject());
                    break;
                case "payment_intent.payment_failed":
                    this.handlePaymentIntentFailed((PaymentIntent) event.getData().getObject());
                    break;
                default:
                    return "Erro desconhecido: " + event.getType();
            }

            return "Webhook recebido com sucesso";
        } catch (SignatureVerificationException e) {
            return "Assinatura inválida";
        } catch (Exception e) {
            return "Erro interno";
        }
    }

    private Long parseCarId(String clientReferenceId) {
        try {
            return Long.parseLong(clientReferenceId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID do carro inválido", e);
        }
    }

    private void handleCheckoutSessionCompleted(Session session) {
        if ("paid".equals(session.getPaymentStatus())) {
            Long carId = parseCarId(session.getClientReferenceId());
            this.rentalService.confirmRental(carId);
        }
    }

    private void handleCheckoutSessionExpired(Session expiredSession) {
        Long carId = parseCarId(expiredSession.getClientReferenceId());
        this.rentalService.expiredRental(carId);
    }

    private void handlePaymentIntentFailed(PaymentIntent paymentIntent) {
        String clientReferenceId = paymentIntent.getMetadata().get("clientReferenceId");

        if (clientReferenceId != null) {
            Long carIdConverted = parseCarId(clientReferenceId);
            String failedPaymentMessage = paymentIntent.getLastPaymentError() != null ?
                    paymentIntent.getLastPaymentError().getMessage() : "Erro desconhecido";
            System.out.println(failedPaymentMessage);
            this.rentalService.cancelRentalByCarId(carIdConverted);
        }
    }
}
