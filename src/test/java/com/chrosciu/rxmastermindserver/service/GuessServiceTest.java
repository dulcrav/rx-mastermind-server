package com.chrosciu.rxmastermindserver.service;

import com.chrosciu.rxmastermindserver.exception.ImproperSampleFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class GuessServiceTest {
    private static final int LENGTH = 4;
    private static final String NUMBERS = "0123456789";

    private GuessService guessService = new GuessService();

    @Test
    public void shouldGenerateCodeWithProperLength() {
        String code = guessService.code();
        assertEquals(LENGTH, code.length());
    }

    @Test
    public void shouldGenerateCodeWithProperContent() {
        String code = guessService.code();
        for (int i = 0; i < code.length(); ++ i) {
            Assertions.assertTrue(NUMBERS.indexOf(code.charAt(i)) >= 0);
        }
    }

    @ParameterizedTest
    @MethodSource("guessTestArgumentsProvider")
    public void shouldGenerateProperResultForGivenCodeAndSample(String code, String sample, String expectedResult) {
        String result = guessService.guess(code, sample);
        assertEquals(expectedResult, result);
    }

    private static Stream<Arguments> guessTestArgumentsProvider() {
        return Stream.of(
                arguments("1234", "1243", "22"),
                arguments("1234", "1234", "40"),
                arguments("1234", "1233", "30"),
                arguments("1234", "4321", "04"),
                arguments("1234", "3321", "03"),
                arguments("1234", "5678", "00")
        );
    }

    @Test
    public void shouldThrowAssertionForImproperCode() {
        String code = "123";
        String sample = "4321";
        Assertions.assertThrows(IllegalArgumentException.class, () -> guessService.guess(code, sample));
    }

    @Test
    public void shouldThrowAssertionForImproperSample() {
        String code = "123";
        String sample = "432";
        Assertions.assertThrows(ImproperSampleFormatException.class, () -> guessService.guess(code, sample));
    }
}
