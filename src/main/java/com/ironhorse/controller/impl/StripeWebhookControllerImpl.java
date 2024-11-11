package com.ironhorse.controller.impl;

import com.ironhorse.service.impl.RentalServiceImpl;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/webhook")
public class StripeWebhookControllerImpl {

    @Value("${api.key.stripe-webhook}")
    private String endpointSecret;
    private final RentalServiceImpl rentalServiceImpl;

    public StripeWebhookControllerImpl(RentalServiceImpl rentalServiceImpl) {
        this.rentalServiceImpl = rentalServiceImpl;
    }

    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        try {

            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

            if ("checkout.session.completed".equals(event.getType())) {
                Session session = (Session) event.getData().getObject();

                if (Objects.equals(session.getPaymentStatus(), "paid")) {
                    Long carId = Long.parseLong(session.getClientReferenceId());
                    this.rentalServiceImpl.confirmRental(carId);
                } else {
                    System.out.println("Pagamento não confirmado");
                }
            }

            return ResponseEntity.ok("Webhook recebido com sucesso");
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Assinatura inválida");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");
        }
    }
}