package com.chrosciu.rxmastermindserver;

import com.chrosciu.rxmastermindserver.repository.ReactiveSessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Slf4j
class RxMastermindServerApplicationTests {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ReactiveSessionRepository reactiveSessionRepository;

    @Test
    void shouldCreateSessionMakeGuessAndDeleteSession() {
        Long sessionId = webTestClient
                .post()
                .uri("/session")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Long.class)
                .returnResult()
                .getResponseBody();
        log.info("{}", sessionId);

        StepVerifier.create(reactiveSessionRepository.findById(sessionId))
                .assertNext(s -> Assertions.assertEquals(sessionId, s.getId()))
                .verifyComplete();

        String result = webTestClient
                .put()
                .uri("/session/{id}/{sample}", sessionId, "1234")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
        log.info("{}", result);

        String result2 = webTestClient
                .put()
                .uri("/session/{id}/{sample}", sessionId, "1234")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
        log.info("{}", result2);

        Assertions.assertEquals(result, result2);

        webTestClient
                .put()
                .uri("/session/{id}/{sample}", sessionId, "A")
                .exchange()
                .expectStatus().isBadRequest();

        webTestClient
                .delete()
                .uri("/session/{id}", sessionId)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();

        webTestClient
                .put()
                .uri("/session/{id}/{sample}", sessionId, "1234")
                .exchange()
                .expectStatus().isNotFound();

        StepVerifier.create(reactiveSessionRepository.findById(sessionId))
                .verifyComplete();

    }

}
