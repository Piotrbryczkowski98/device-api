package com.assessment.device.domain.exception;

public class IllegalDeviceStateException extends RuntimeException {
    public IllegalDeviceStateException(String message) {
        super(message);
    }
}