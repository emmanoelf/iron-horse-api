package com.ironhorse.service.impl;

import com.ironhorse.dto.EmailDto;
import com.ironhorse.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private static final String FROM = "no-reply@uvio.com.br";

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(EmailDto email) {
        try{
            this.validateEntryData(email);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, true);

            mimeHelper.setFrom(FROM);
            mimeHelper.setTo(email.to());
            mimeHelper.setSubject(email.subject());

            String body = this.loadTemplate(email.body());

            if (email.templateVariables() != null && !email.templateVariables().isEmpty()) {
                body = populateTemplate(body, email.templateVariables());
            }

            mimeHelper.setText(body, true);

            this.mailSender.send(mimeMessage);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String loadTemplate(String templateName) throws IOException {
        ClassPathResource resource = new ClassPathResource("/templates/" + templateName + ".html");
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    private void validateEntryData(EmailDto email) {
        if(Objects.isNull(email.to()) || email.to().isEmpty()){
            throw new IllegalArgumentException("Destinatário não fornecido");
        }

        if(Objects.isNull(email.subject()) || email.subject().isEmpty()){
            throw new IllegalArgumentException("Assunto não fornecido");
        }

        if(Objects.isNull(email.body()) || email.body().isEmpty()){
            throw new IllegalArgumentException("Corpo não fornecido");
        }
    }

    private String populateTemplate(String body, Map<String, String> templateVariables) {
        for (Map.Entry<String, String> entry : templateVariables.entrySet()) {
            body = body.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return body;
    }
}
