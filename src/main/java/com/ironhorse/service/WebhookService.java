package com.ironhorse.service;

public interface WebhookService {
    String handleWebhook(String payload, String sigHeader);
}
