package com.barbersync.api.shared.exceptions;

public class CloudinaryOperationException extends RuntimeException {

    public CloudinaryOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}