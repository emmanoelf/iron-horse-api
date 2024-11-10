package com.ironhorse.service;

import com.ironhorse.dto.EmailDto;

import java.io.IOException;

public interface EmailService {
    void sendEmail(EmailDto email);
    String loadTemplate(String templateName) throws IOException;
}
