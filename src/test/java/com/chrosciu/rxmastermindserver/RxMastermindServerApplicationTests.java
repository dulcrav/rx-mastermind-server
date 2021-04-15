package com.chrosciu.rxmastermindserver;

import com.chrosciu.rxmastermindserver.repository.ReactiveSessionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

@SpringBootTest
@ActiveProfiles("test")
class RxMastermindServerApplicationTests {

    @Autowired
    private ReactiveSessionRepository reactiveSessionRepository;

    @Test
    void sessionDatabaseSmokeTest() {
        StepVerifier.create(reactiveSessionRepository.findById(1L))
                .verifyComplete();
    }

}
