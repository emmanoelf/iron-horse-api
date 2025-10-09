package com.ironhorse.unit.service;

import com.ironhorse.dto.EmailDto;
import com.ironhorse.service.impl.EmailServiceImpl;
import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private JavaMailSender javaMailSender;

    private EmailDto mockEmailDto;

    @BeforeEach()
    public void setUp(){
        this.mockEmailDto = new EmailDto(
                "destinatario@email.com",
                "Bem-vindo!",
                "template-teste",
                Map.of("name", "John"));
    }

    @Test
    @DisplayName("Should be able to send an email to user")
    public void shouldBeAbleToSendAnEmailToUser() throws Exception{
        MimeMessage mimeMessage = this.createFakeMimeMessage();
        when(this.javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        EmailServiceImpl spyService = spy(this.emailService);
        doReturn("<html>Olá, {{name}}</html>").when(spyService).loadTemplate("template-teste");

        spyService.sendEmail(this.mockEmailDto);

        ArgumentCaptor<MimeMessage> mimeCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(this.javaMailSender).send(mimeCaptor.capture());
        MimeMessage sentMessage = mimeCaptor.getValue();

        assertEquals("no-reply@uvio.com.br", ((InternetAddress) sentMessage.getFrom()[0]).getAddress());
        assertEquals(this.mockEmailDto.to(),
                ((InternetAddress) sentMessage.getRecipients(MimeMessage.RecipientType.TO)[0]).getAddress());
        assertEquals(mockEmailDto.subject(), sentMessage.getSubject());

        Object content = sentMessage.getContent();
        assertInstanceOf(Multipart.class, content);

        String bodyContent = this.getBodyContent((Multipart) content);

        assertTrue(bodyContent.contains("Olá, John"));
    }

    private String getBodyContent(Multipart content) throws MessagingException, IOException {
        BodyPart bodyPart = content.getBodyPart(0);
        Object partContent = bodyPart.getContent();

        if (partContent instanceof String) {
            return (String) partContent;
        }

        if (partContent instanceof Multipart innerMultipart) {
            BodyPart innerBodyPart = innerMultipart.getBodyPart(0);
            return (String) innerBodyPart.getContent();
        }

        throw new IllegalStateException("Tipo de conteúdo inesperado: " + partContent.getClass());
    }

    private MimeMessage createFakeMimeMessage(){
        return new MimeMessage(Session.getDefaultInstance(new Properties()));
    }

    @Test
    @DisplayName("Should not be able to send email if recipient is null")
    public void shouldNotBeAbleToSendEmailIfRecipientIsNull(){
        EmailDto emailWithoutRecipient = new EmailDto(null, "Subject", "template", Map.of());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> this.emailService.sendEmail(emailWithoutRecipient));

        assertEquals("Destinatário não fornecido", exception.getMessage());
    }

    @Test
    @DisplayName("Should not be able to send email if recipient is empty")
    public void shouldNotBeAbleToSendEmailIfRecipientIsEmpty(){
        EmailDto emailWithoutRecipient = new EmailDto("", "Subject", "template", Map.of());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> this.emailService.sendEmail(emailWithoutRecipient));

        assertEquals("Destinatário não fornecido", exception.getMessage());
    }

    @Test
    @DisplayName("Should not be able to send email if subject is null")
    public void shouldNotBeAbleToSendEmailIfSubjectIsNull(){
        EmailDto emailWithoutSubject = new EmailDto("destinatario@email.com", null, "template", Map.of());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> this.emailService.sendEmail(emailWithoutSubject));

        assertEquals("Assunto não fornecido", exception.getMessage());
    }

    @Test
    @DisplayName("Should not be able to send email if subject is empty")
    public void shouldNotBeAbleToSendEmailIfSubjectIsEmpty(){
        EmailDto emailWithoutSubject = new EmailDto("destinatario@email.com", "", "template", Map.of());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> this.emailService.sendEmail(emailWithoutSubject));

        assertEquals("Assunto não fornecido", exception.getMessage());
    }

    @Test
    @DisplayName("Should not be able to send email if boyd is null")
    public void shouldNotBeAbleToSendEmailIfBodyIsNull(){
        EmailDto emailWithoutBody = new EmailDto("destinatario@email.com", "Subject", null, Map.of());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> this.emailService.sendEmail(emailWithoutBody));

        assertEquals("Corpo não fornecido", exception.getMessage());
    }

    @Test
    @DisplayName("Should not be able to send email if body is empty")
    public void shouldNotBeAbleToSendEmailIfBodyIsEmpty(){
        EmailDto emailWithoutBody = new EmailDto("destinatario@email.com", "Subject", "", Map.of());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> this.emailService.sendEmail(emailWithoutBody));

        assertEquals("Corpo não fornecido", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw Runtime Exception if template is missing")
    public void shouldThrowRuntimeExceptionIfTemplateIsMissing() throws IOException {
        EmailServiceImpl spyEmailService = spy(this.emailService);
        doThrow(new IOException("Erro ao processar e-mail")).when(spyEmailService).loadTemplate("template-teste");

        MimeMessage mimeMessage = this.createFakeMimeMessage();
        when(this.javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> spyEmailService.sendEmail(this.mockEmailDto));
        assertEquals("Erro ao processar e-mail", exception.getMessage());
    }

    @Test
    @DisplayName("Should load and use real HTML template from ClassPath")
    public void shouldLoadAndUseRealHTMLTemplateFromClassPath() throws IOException {
        String templateContent = this.emailService.loadTemplate("template-teste");
        assertTrue(templateContent.contains("{{name}}"));
    }
}
