package com.ironhorse.controller.impl;

import com.ironhorse.controller.WebhookController;
import com.ironhorse.service.WebhookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class StripeWebhookControllerImpl implements WebhookController {

    private final WebhookService webhookService;

    public StripeWebhookControllerImpl(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @Override
    @PostMapping("/stripe")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        String response = this.webhookService.handleWebhook(payload, sigHeader);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}