package com.chrosciu.rxmastermindserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class RxMastermindServerApplicationTests {

    @Test
    void contextLoads() {
    }

}
