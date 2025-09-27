package com.example.bankcards.exception;

public class AccessDeniedToCardException extends RuntimeException {
    public AccessDeniedToCardException() {
        super("Access denied to card");
    }
}
