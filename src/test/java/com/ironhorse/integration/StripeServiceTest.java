package com.ironhorse.integration;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.ironhorse.client.StripeClient;
import com.ironhorse.dto.PaymentDto;
import com.ironhorse.dto.PaymentResponseDto;
import com.ironhorse.service.impl.StripeServiceImpl;
import com.stripe.Stripe;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("integration")
@Tag("external")
@ExtendWith(SpringExtension.class)
public class StripeServiceTest {
    private WireMockServer wireMockServer;

    @BeforeEach
    public void setup() {
        this.wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8090));
        this.wireMockServer.start();
        WireMock.configureFor("localhost", this.wireMockServer.port());

        Stripe.overrideApiBase("http://localhost:8090");
        Stripe.apiKey = "sk_test_fake";
    }

    @AfterEach
    public void teardown() {
        if (this.wireMockServer != null) {
            this.wireMockServer.stop();
        }
    }

    @Test
    @DisplayName("Should be able to create a payment link successfully")
    public void shouldCreatePaymentLink() throws Exception {
        String sessionId = "session_test_123";
        String fakeUrl = "https://mock.stripe.com/session/123";

        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/v1/checkout/sessions"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"id\": \"" + sessionId + "\", \"url\": \"" + fakeUrl + "\" }")));

        StripeClient stripeClient = new StripeClient();
        StripeServiceImpl stripeService = new StripeServiceImpl(stripeClient);
        PaymentResponseDto response = stripeService.createPaymentLink(this.mockPaymentDto());

        assertEquals("https://mock.stripe.com/session/123", response.url());
        WireMock.verify(WireMock.postRequestedFor(WireMock.urlEqualTo("/v1/checkout/sessions")));
    }

    private PaymentDto mockPaymentDto() {
        return new PaymentDto(1L, "Datsun 240Z", 1L, 500L, "Um dos carros mais bonitos já feito", "http://path-to-image.com");
    }
}