package com.ironhorse.integration.internal.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@Tag("internal")
@Tag("slow")
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SecurityConfigIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String ORIGIN = "Origin";
    private static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";

    @Test
    @DisplayName("It should be able to access swagger without authentication")
    public void itShouldBeAbleToAccessLoginWithoutAuthentication() throws Exception {
        this.mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("It should deny access to protect endpoint without auth")
    public void itShouldDenyAccessToProtectEndpointWithoutAuth() throws Exception {
        this.mockMvc.perform(get("/v1/rentals"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("It should be able to return expected CORS headers")
    public void itShouldBeAbleToReturnExpectedCORSHeaders() throws Exception {
        this.mockMvc.perform(options("/v1/cars/search")
                            .header(ORIGIN, "http://localhost:3000")
                            .header(ACCESS_CONTROL_REQUEST_METHOD, "GET"))
                .andExpect(status().isOk())
                .andExpect(header().string(ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:3000"))
                .andExpect(header().string(ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,PUT,DELETE"));
    }

}
