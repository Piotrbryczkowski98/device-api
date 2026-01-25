package com.assessment.device.api;

import com.assessment.device.domain.exception.DeviceNotFoundException;
import com.assessment.device.domain.exception.IllegalDeviceStateException;
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

    @ExceptionHandler(DeviceNotFoundException.class)
    public ProblemDetail handleNotFound(DeviceNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }
}