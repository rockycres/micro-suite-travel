package com.learn.gatewayservice;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.PostConstruct;



import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Resilience4JControllerIntegrationTest {

    @LocalServerPort
    int port;
    private WebTestClient client;

    @BeforeEach
    public void setup() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("pathRouteWorks")
    @Disabled
    public void pathRouteWorks() {
        client.get().uri("/customer-service/api-customer/v1/customer/{id}", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Map.class)
                .consumeWith(result -> {
                    assertThat(result.getResponseBody()).isNotEmpty();
                });
    }


    @Test
    @SuppressWarnings("unchecked")
    @Disabled
    public void circuitBreakerFallbackRouteWorks() {
        client.get().uri("/fallback-service/api-fallback/v1/")
                .exchange()
                .expectStatus().isTemporaryRedirect()
                .expectBody()
                .jsonPath("$.data.responseMsg").isEqualTo("Fallback response from dummy endpoint");

    }
}
/*
    @Test
    public void rateLimiterWorks() {
        WebTestClient authClient = client.mutate()
                .filter(basicAuthentication("user", "password"))
                .build();

        boolean wasLimited = false;

        for (int i = 0; i < 20; i++) {
            FluxExchangeResult<Map> result = authClient.get()
                    .uri("/anything/1")
                    .header("Host", "www.limited.org")
                    .exchange()
                    .returnResult(Map.class);
            if (result.getStatus().equals(HttpStatus.TOO_MANY_REQUESTS)) {
                System.out.println("Received result: "+result);
                wasLimited = true;
                break;
            }
        }

        assertThat(wasLimited)
                .as("A HTTP 429 TOO_MANY_REQUESTS was not received")
                .isTrue();

    }

*/

