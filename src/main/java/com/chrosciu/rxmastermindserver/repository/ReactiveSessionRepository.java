package com.chrosciu.rxmastermindserver.repository;

import com.chrosciu.rxmastermindserver.model.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
@RequiredArgsConstructor
public class ReactiveSessionRepository {
    private final SessionRepository sessionRepository;

    public Mono<Session> save(Session session) {
        return Mono.fromCallable(() -> sessionRepository.save(session)).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Session> findById(long id) {
        return Mono.defer(() -> Mono.justOrEmpty(sessionRepository.findById(id))).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Void> deleteById(long id) {
        return Mono.fromRunnable(() -> {
            sessionRepository.deleteById(id);
        }).onErrorResume(e -> Mono.empty()).then().subscribeOn(Schedulers.boundedElastic());
    }
}
