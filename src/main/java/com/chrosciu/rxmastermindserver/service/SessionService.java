package com.chrosciu.rxmastermindserver.service;

import com.chrosciu.rxmastermindserver.exception.SessionNotFoundException;
import com.chrosciu.rxmastermindserver.model.Session;
import com.chrosciu.rxmastermindserver.repository.ReactiveSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final GuessService guessService;
    private final ReactiveSessionRepository reactiveSessionRepository;

    public Mono<Long> create() {
        String code = guessService.code();
        Session session = Session.builder().code(code).build();
        return reactiveSessionRepository.save(session).map(Session::getId);
    }

    public Mono<String> guess(long id, String sample) {
        return reactiveSessionRepository.findById(id)
                .map(s -> guessService.guess(s.getCode(), sample))
                .switchIfEmpty(Mono.error(new SessionNotFoundException()));
    }

    public Mono<Void> destroy(long id) {
        return reactiveSessionRepository.deleteById(id);
    }

}
