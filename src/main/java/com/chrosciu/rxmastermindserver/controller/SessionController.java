package com.chrosciu.rxmastermindserver.controller;

import com.chrosciu.rxmastermindserver.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @PostMapping()
    Mono<Long> newGame() {
        return sessionService.createNewGame();
    }

    @PutMapping("{id}/{sample}")
    Mono<String> guess(@PathVariable("id") Long id, @PathVariable("sample") String sample) {
        return sessionService.guess(id, sample);
    }

    @DeleteMapping("/{id}")
    Mono<Void> deleteSession(@PathVariable Long id) {
        return sessionService.deleteSession(id);
    }

}
