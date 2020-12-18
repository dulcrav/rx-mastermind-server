package com.chrosciu.rxmastermindserver.service;

import com.chrosciu.rxmastermindserver.exception.SessionNotFoundException;
import com.chrosciu.rxmastermindserver.model.Session;
import com.chrosciu.rxmastermindserver.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final GuessService guessService;
    private final SessionRepository sessionRepository;

    public Mono<String> create() {
        String code = guessService.code();
        Session session = Session.builder().code(code).build();
        return sessionRepository.save(session).map(Session::getId);
    }

    public Mono<String> guess(String id, String sample) {
        return sessionRepository.findById(id)
                .map(s -> guessService.guess(s.getCode(), sample))
                .switchIfEmpty(Mono.error(new SessionNotFoundException()));
    }

    public Mono<Void> destroy(String id) {
        return sessionRepository.deleteById(id);
    }

}
