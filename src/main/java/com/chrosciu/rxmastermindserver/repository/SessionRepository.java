package com.chrosciu.rxmastermindserver.repository;

import com.chrosciu.rxmastermindserver.model.Session;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends ReactiveCrudRepository<Session, Long> {
}
