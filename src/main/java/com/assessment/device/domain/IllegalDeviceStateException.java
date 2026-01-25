package com.assessment.device.domain;

public class IllegalDeviceStateException extends RuntimeException {
    public IllegalDeviceStateException(String message) {
        super(message);
    }
}