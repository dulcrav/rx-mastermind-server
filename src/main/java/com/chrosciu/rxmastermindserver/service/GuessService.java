package com.chrosciu.rxmastermindserver.service;

import com.chrosciu.rxmastermindserver.exception.ImproperSampleFormatException;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GuessService {
    private static final int LENGTH = 4;
    private static final String NUMBERS = "0123456789";

    public String code() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < LENGTH; i++) {
            sb.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        }
        return sb.toString();
    }

    public String guess(@NonNull String code, @NonNull String sample) {
        if (sample.length() != LENGTH) {
            throw new ImproperSampleFormatException();
        }
        if (code.length() != LENGTH) {
            throw new IllegalArgumentException();
        }
        int[] tempCode = new int[LENGTH];
        int[] tempSample = new int[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            tempCode[i] = code.charAt(i);
            tempSample[i] = sample.charAt(i);
        }

        int exactPositionMatches = 0;
        for (int i = 0; i < LENGTH; i++) {
            if (tempCode[i] == tempSample[i]) {
                exactPositionMatches++;
                tempCode[i] = -1;
                tempSample[i] = -1;
            }
        }

        int differentPositionMatches = 0;
        for (int i = 0; i < LENGTH; i++) {
            if (tempCode[i] == -1) {
                continue;
            }
            for (int j = 0; j < LENGTH; j++) {
                if (tempSample[j] == -1) {
                    continue;
                }
                if (tempCode[i] == tempSample[j]) {
                    differentPositionMatches++;
                    tempCode[i] = -1;
                    tempSample[j] = -1;
                }
            }
        }
        return String.format("%d%d", exactPositionMatches, differentPositionMatches);
    }
}
