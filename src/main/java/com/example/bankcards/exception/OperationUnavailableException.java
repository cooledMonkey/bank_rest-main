package com.example.bankcards.exception;

public class OperationUnavailableException extends RuntimeException {
    public OperationUnavailableException() {
        super("Operation unavailable");
    }
}
