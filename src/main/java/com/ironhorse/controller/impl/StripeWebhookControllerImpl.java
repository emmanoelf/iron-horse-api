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
                // Extrair a session
                Session session = (Session) event.getData().getObject();

                // Verificar o status do pagamento
                if (Objects.equals(session.getPaymentStatus(), "PAID")) {
                    // O pagamento foi confirmado, agora você pode salvar a locação no banco
                    Long carId = Long.parseLong(session.getClientReferenceId()); // ou outro campo com ID do carro
                    // Aqui você pode invocar um serviço para salvar a locação no banco
                    this.rentalServiceImpl.confirmRental(carId);
                } else {
                    // O pagamento não foi bem-sucedido
                    System.out.println("Pagamento não confirmado");
                }
            }

            return ResponseEntity.ok("Webhook recebido com sucesso");
        } catch (SignatureVerificationException e) {
            // Assinatura inválida
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Assinatura inválida");
        } catch (Exception e) {
            // Outros erros
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");
        }
    }
}