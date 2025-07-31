package com.euleops.auth.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiError> handleAuthException(AuthException ex, HttpServletRequest req, Locale locale) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), req, locale);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest req, Locale locale) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "error.invalid.param.type", req, locale);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(Exception ex, HttpServletRequest req, Locale locale) {
        log.error("Unhandled exception", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "error.unexpected", req, locale);
    }

    private ResponseEntity<ApiError> buildErrorResponse(HttpStatus status, String messageKey, HttpServletRequest req, Locale locale) {
        String localizedMessage = messageSource.getMessage(messageKey, null, messageKey, locale);

        return ResponseEntity.status(status).body(ApiError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(localizedMessage)
                .path(req.getRequestURI())
                .build());
    }

}
