package com.chrosciu.rxmastermindserver.repository;

import com.chrosciu.rxmastermindserver.model.Session;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends ReactiveMongoRepository<Session, String> {
}
