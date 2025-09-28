package com.example.bankcards.exception;

import com.example.bankcards.dto.ExceptionResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException e) {
        ExceptionResponse response = new ExceptionResponse("Неверные учетные данные");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(UserNotFoundException e) {
        ExceptionResponse response = new ExceptionResponse("Пользователь не найден");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(CardNotFoundException e) {
        ExceptionResponse response = new ExceptionResponse("Карта не найдена");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AccessDeniedToCardException.class)
    public ResponseEntity<ExceptionResponse> handleException(AccessDeniedToCardException e) {
        ExceptionResponse response = new ExceptionResponse("Доступ к карте невожможен");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(RoleNotFoundException e) {
        ExceptionResponse response = new ExceptionResponse("Роль не найдена");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
