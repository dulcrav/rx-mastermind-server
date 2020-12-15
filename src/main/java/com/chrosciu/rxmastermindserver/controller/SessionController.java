package com.chrosciu.rxmastermindserver.controller;

import com.chrosciu.rxmastermindserver.exception.ImproperSampleFormatException;
import com.chrosciu.rxmastermindserver.exception.SessionNotFoundException;
import com.chrosciu.rxmastermindserver.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionController {
    private final SessionService sessionService;

    @PostMapping
    public Mono<String> create() {
        return sessionService.create();
    }

    @PutMapping("/{id}/{sample}")
    public Mono<String> guess(@PathVariable("id") String id, @PathVariable("sample") String sample) {
        return sessionService.guess(id, sample);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> destroy(@PathVariable("id") String id) {
        return sessionService.destroy(id);
    }

    @ExceptionHandler(SessionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<String> handleSessionNotFoundException(SessionNotFoundException e) {
        return Mono.just("Session not found");
    }

    @ExceptionHandler(ImproperSampleFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<String> handleImproperSampleFormatException(ImproperSampleFormatException e) {
        return Mono.just("Improper format of sample");
    }


}
