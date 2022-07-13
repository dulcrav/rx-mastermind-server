package com.chrosciu.rxmastermindserver.service;

import com.chrosciu.rxmastermindserver.exception.ImproperSampleFormatException;
import com.chrosciu.rxmastermindserver.exception.SessionNotFoundException;
import com.chrosciu.rxmastermindserver.model.Session;
import com.chrosciu.rxmastermindserver.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final GuessService guessService;
    private final SessionRepository sessionRepository;

    public Mono<Long> createNewGame() {
        return Mono.fromCallable(() -> sessionRepository.save(Session.builder()
                        .code(guessService.code())
                        .build()))
                .map(Session::getId)
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<String> guess(Long id, String sample) {
        return Mono.fromCallable(() -> sessionRepository.findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .map(session -> {
                    validateCode(sample);
                    return guessService.guess(session
                            .map(Session::getCode)
                            .orElseThrow(SessionNotFoundException::new), sample);
                })
                .onErrorMap(Exceptions::propagate);
    }

    public Mono<Void> deleteSession(Long id) {
        return Mono.fromRunnable(() -> sessionRepository.deleteById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .then(Mono.empty());
    }

    private void validateCode(String sample) {
        if (!sample.matches("^\\d{4}$")) {
            throw new ImproperSampleFormatException();
        }
    }
}
