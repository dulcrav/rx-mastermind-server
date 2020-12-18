package com.chrosciu.rxmastermindserver.controller;

import com.chrosciu.rxmastermindserver.service.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SessionControllerTest {
    @Mock
    private SessionService sessionService;
    @InjectMocks
    private SessionController sessionController;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        webTestClient = WebTestClient
                .bindToController(sessionController)
                .build();
    }

    @Test
    public void shouldCreateSessionAndReturnSessionId() {
        String someSessionId = "baadf00d";
        when(sessionService.create()).thenReturn(Mono.just(someSessionId));
        webTestClient
                .post()
                .uri("/session")
                .exchange()
                .expectStatus().isOk()
                .expectBody().equals(someSessionId);

    }
}
