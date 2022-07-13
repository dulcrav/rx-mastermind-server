package com.chrosciu.rxmastermindserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImproperSampleFormatException extends RuntimeException {
}
