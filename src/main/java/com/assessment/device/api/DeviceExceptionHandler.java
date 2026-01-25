package com.assessment.device.api;

import com.assessment.device.domain.IllegalDeviceStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DeviceExceptionHandler {

    @ExceptionHandler(IllegalDeviceStateException.class)
    public ProblemDetail handleIllegalState(IllegalDeviceStateException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}