package com.ironhorse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Webhook controller")
public interface WebhookController {
    @Operation(summary = "Receives webhook events to update car rental status payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Receives in real-time notification from webhook about payment events. " +
                            "Based on the events received, it performs actions such as confirming, " +
                            "cancelling or marking as expired a specific car rental",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
    })
    ResponseEntity<String> handleStripeWebhook(String payload, String sigHeader);
}
