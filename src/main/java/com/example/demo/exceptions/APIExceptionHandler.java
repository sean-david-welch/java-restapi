
package com.example.demo.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class APIExceptionHandler {

    private static final Logger logger = LogManager.getLogger(APIExceptionHandler.class);

    @ExceptionHandler(value = { APIRequestException.class })
    public ResponseEntity<Object> handleAPIRequestException(APIRequestException exception) {
        logger.error("APIRequestException: ", exception);

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = new ErrorResponse(
                exception.getMessage(),
                status,
                ZonedDateTime.now(ZoneId.of("Z")));

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(value = { NullPointerException.class })
    public ResponseEntity<Object> handleNullPointerException(NullPointerException exception) {
        logger.error("NullPointerException: ", exception);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponse errorResponse = new ErrorResponse(
                "Null pointer exception",
                status,
                ZonedDateTime.now(ZoneId.of("Z")));

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception) {
        logger.error("IllegalArgumentException: ", exception);

        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception) {
        logger.error("AccessDeniedException: ", exception);

        return buildErrorResponse(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException exception) {
        logger.error("ArgumentNotValidException: ", exception);

        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { HttpMessageNotReadableException.class })
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        logger.error("MessageUnreadableException: ", exception);

        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception, HttpStatus status) {
        logger.error("Exception: ", exception);

        ErrorResponse errorResponse = new ErrorResponse(
                exception.getMessage(),
                status,
                ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(errorResponse, status);
    }

}
