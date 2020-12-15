package com.chrosciu.rxmastermindserver.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GuessServiceTest {
    private static final int LENGTH = 4;
    private static final String NUMBERS = "0123456789";

    private GuessService guessService = new GuessService();

    @Test
    public void shouldGenerateCodeWithProperLength() {
        String code = guessService.code();
        Assertions.assertEquals(LENGTH, code.length());
    }

    @Test
    public void shouldGenerateCodeWithProperContent() {
        String code = guessService.code();
        for (int i = 0; i < code.length(); ++ i) {
            Assertions.assertTrue(NUMBERS.indexOf(code.charAt(i)) >= 0);
        }
    }

    @Test
    public void shouldGenerateProperResult1() {
        String code = "1234";
        String sample = "1243";
        String result = guessService.guess(code, sample);
        Assertions.assertEquals("22", result);
    }

    @Test
    public void shouldGenerateProperResult2() {
        String code = "1234";
        String sample = "1234";
        String result = guessService.guess(code, sample);
        Assertions.assertEquals("40", result);
    }

    @Test
    public void shouldGenerateProperResult3() {
        String code = "1234";
        String sample = "1233";
        String result = guessService.guess(code, sample);
        Assertions.assertEquals("30", result);
    }

    @Test
    public void shouldGenerateProperResult4() {
        String code = "1234";
        String sample = "4321";
        String result = guessService.guess(code, sample);
        Assertions.assertEquals("04", result);
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
        Assertions.assertThrows(IllegalArgumentException.class, () -> guessService.guess(code, sample));
    }
}
