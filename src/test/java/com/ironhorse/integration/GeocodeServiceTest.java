package com.ironhorse.integration;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.ironhorse.dto.googleGeoCode.LocationDto;
import com.ironhorse.service.GeocodeService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("integration")
@Tag("external")
@Tag("slow")
@SpringBootTest
@ActiveProfiles("test")
class GeocodeServiceTest {
    private static WireMockServer wireMockServer;

    @BeforeAll
    public static void beforeAll() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
    }

    @AfterAll
    public static void afterAll() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("api.key.google-maps", () -> "test_key");
        registry.add("googleGeocodeClient.url", () -> wireMockServer.baseUrl() + "/maps/api/geocode");
    }

    @Autowired
    private GeocodeService geocodeService;

    @Test
    @DisplayName("Should be able to return coordinates for a valid address (WireMock)")
    void shouldBeAbleToReturnCoordinatesForValidAddress() {
        wireMockServer.stubFor(get(urlPathEqualTo("/maps/api/geocode/json"))
                .withQueryParam("address", equalTo("Rua Falsa, 123"))
                .withQueryParam("key", equalTo("test_key"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                  "results": [
                                    {
                                      "geometry": {
                                        "location": {
                                          "lat": -23.55052,
                                          "lng": -46.633308
                                        }
                                      }
                                    }
                                  ],
                                  "status": "OK"
                                }
                                """))
        );

        LocationDto location = geocodeService.getLatitudeAndLongitude("Rua Falsa, 123");

        assertThat(location).isNotNull();
        assertThat(location.lat()).isEqualTo(-23.55052);
        assertThat(location.lng()).isEqualTo(-46.633308);

        wireMockServer.verify(getRequestedFor(urlPathEqualTo("/maps/api/geocode/json"))
                .withQueryParam("address", equalTo("Rua Falsa, 123"))
                .withQueryParam("key", equalTo("test_key")));
    }

    @Test
    @DisplayName("Should throw RuntimeException when no results are returned")
    void shouldThrowExceptionWhenNoResultsAreReturned() {
        wireMockServer.stubFor(get(urlPathEqualTo("/maps/api/geocode/json"))
                .withQueryParam("address", equalTo("Rua Inexistente, 999"))
                .withQueryParam("key", equalTo("test_key"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                              "results": [],
                              "status": "ZERO_RESULTS"
                            }
                            """)));

        assertThatThrownBy(() -> geocodeService.getLatitudeAndLongitude("Rua Inexistente, 999"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Não foi possível obter os dados");

        wireMockServer.verify(getRequestedFor(urlPathEqualTo("/maps/api/geocode/json"))
                .withQueryParam("address", equalTo("Rua Inexistente, 999"))
                .withQueryParam("key", equalTo("test_key")));
    }
}