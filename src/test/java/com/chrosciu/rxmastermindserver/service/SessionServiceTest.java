package com.chrosciu.rxmastermindserver.service;

import com.chrosciu.rxmastermindserver.model.Session;
import com.chrosciu.rxmastermindserver.repository.SessionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {
    @Mock
    private GuessService guessService;
    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private SessionService sessionService;

    @Test
    public void shouldDestroySessionWithGivenId() {
        //given
        String someSessionId = "baadf00d";
        Mono<Void> success = Mono.empty();
        when(sessionRepository.deleteById(someSessionId))
                .thenReturn(success);

        //when
        Mono<Void> result = sessionService.destroy(someSessionId);

        //then
        Assertions.assertEquals(success, result);
    }

    @Test
    public void shouldDestroySessionWithGivenIdUsingTestPublisher() {
        //given
        String someSessionId = "baadf00d";
        TestPublisher<Void> publisher = TestPublisher.createCold();
        Mono<Void> success = publisher.mono();
        when(sessionRepository.deleteById(someSessionId))
                .thenReturn(success);

        //when
        Mono<Void> result = sessionService.destroy(someSessionId);

        //then
        StepVerifier.create(result)
                .expectSubscription()
                .expectNoEvent(Duration.of(1, ChronoUnit.SECONDS))
                .thenCancel()
                .verify();

        //when
        publisher.complete();

        //then
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    public void shouldCreateSessionWithProperCodeAndReturnSavedId() {
        //given
        String someSessionId = "baadf00d";
        String someCode = "1234";
        when(guessService.code()).thenReturn(someCode);
        when(sessionRepository.save(Mockito.any())).then(invocationOnMock -> {
            Session session = invocationOnMock.getArgument(0);
            session.setId(someSessionId);
            return Mono.just(session);
        });

        //when
        Mono<String> result = sessionService.create();

        //then
        ArgumentCaptor<Session> captor = ArgumentCaptor.forClass(Session.class);
        Mockito.verify(sessionRepository).save(captor.capture());
        Assertions.assertEquals(someCode, captor.getValue().getCode());

        //then
        StepVerifier.create(result)
                .expectNext(someSessionId)
                .verifyComplete();
    }
}
