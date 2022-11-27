package com.upstream.py.ex.exception.exeptionMessages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
public class ApiExceptionMessage {

    private final String message;
    private final HttpStatus httpStatus;
    private Date timeStamp;
}
