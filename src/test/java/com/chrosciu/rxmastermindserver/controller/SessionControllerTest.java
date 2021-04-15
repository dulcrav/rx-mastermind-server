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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
        long someSessionId = 3;
        when(sessionService.create()).thenReturn(Mono.just(someSessionId));
        webTestClient
                .post()
                .uri("/session")
                .exchange()
                .expectStatus().isOk()
                .expectBody().equals(someSessionId);

    }

    @Test
    public void shouldReturnGuessResultForGivenSampleAndSessionWithGivenId() {
        long someSessionId = 3;
        String someSample = "1234";
        String someGuess = "23";
        when(sessionService.guess(someSessionId, someSample)).thenReturn(Mono.just(someGuess));
        webTestClient
                .put()
                .uri("/session/{sessionId}/{sample}", someSessionId, someSample)
                .exchange()
                .expectStatus().isOk()
                .expectBody().equals(someGuess);
    }

    @Test
    public void shouldDestroySessionWithGivenId() {
        long someSessionId = 3;
        when(sessionService.destroy(someSessionId)).thenReturn(Mono.empty());
        webTestClient
                .delete()
                .uri("/session/{sessionId}", someSessionId)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
        verify(sessionService).destroy(someSessionId);
    }


}
