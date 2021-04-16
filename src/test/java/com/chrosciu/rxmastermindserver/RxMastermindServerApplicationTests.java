package com.chrosciu.rxmastermindserver;

import com.chrosciu.rxmastermindserver.repository.SessionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
class RxMastermindServerApplicationTests {

    @Autowired
    SessionRepository sessionRepository;

    @Test
    void contextLoads() {
        StepVerifier.create(sessionRepository.findAll())
                .verifyComplete();
    }

}
