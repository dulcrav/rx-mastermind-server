package com.chrosciu.rxmastermindserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class RxMastermindServerApplication {

    public static void main(String[] args) {
        BlockHound.install();
        SpringApplication.run(RxMastermindServerApplication.class, args);
    }

}
