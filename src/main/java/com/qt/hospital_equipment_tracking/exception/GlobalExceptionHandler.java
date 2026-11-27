package com.qt.hospital_equipment_tracking.exception;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Date;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.dao.DataIntegrityViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private String getRequestPath(WebRequest request) {
        if (request instanceof ServletWebRequest servletWebRequest) {
            return servletWebRequest.getRequest().getRequestURI();
        }
        return null;
    }

    @ExceptionHandler(DuplicateResourceException.class)
    protected ResponseEntity<Object> handleDuplicateResourceException(DuplicateResourceException ex, WebRequest request) {
        ErrorObject errorObject = new ErrorObject(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                new Date(),
                "DUPLICATE_RESOURCE",
                getRequestPath(request)
        );

        return new ResponseEntity<>(errorObject, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BusinessRuleViolationException.class)
    protected ResponseEntity<Object> handleBusinessRuleViolationException(BusinessRuleViolationException ex, WebRequest request) {
        ErrorObject errorObject = new ErrorObject(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                new Date(),
                "BUSINESS_RULE_VIOLATION",
                getRequestPath(request)
        );

        return new ResponseEntity<>(errorObject, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
        ErrorObject errorObject = new ErrorObject(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                new Date(),
                "BAD_REQUEST",
                getRequestPath(request)
        );

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorObject errorObject = new ErrorObject(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                new Date(),
                "RESOURCE_NOT_FOUND",
                getRequestPath(request)
        );

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    // Handle Spring's built-in optimistic locking exception.
    @ExceptionHandler(OptimisticLockingFailureException.class)
    protected ResponseEntity<Object> handleOptimisticLockingFailureException(
            OptimisticLockingFailureException ex, WebRequest request) {

        ErrorObject errorObject = new ErrorObject(
                HttpStatus.CONFLICT.value(),
                "The resource has been modified by another request. Please refresh and try again.",
                new Date(),
                "OPTIMISTIC_LOCK_CONFLICT",
                getRequestPath(request)
        );

        return new ResponseEntity<>(errorObject, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(
            NoResourceFoundException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ErrorObject errorObject = new ErrorObject(
                status.value(),
                "The requested resource was not found.",
                new Date(),
                "ENDPOINT_NOT_FOUND",
                getRequestPath(request)
        );

        return new ResponseEntity<>(errorObject, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, String> fieldErrors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
            );
        }

        ErrorObject errorObject = new ErrorObject(
                status.value(),
                "Request validation failed.",
                new Date(),
                "VALIDATION_FAILED",
                getRequestPath(request),
                fieldErrors
        );

        return new ResponseEntity<>(errorObject, headers, status);
    }

    // enum invalid or data type dismatched or JSON format incorrect
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ErrorObject errorObject = new ErrorObject(
                status.value(),
                "Invalid or malformed request body.",
                new Date(),
                "INVALID_REQUEST_BODY",
                getRequestPath(request)
        );

        return new ResponseEntity<>(errorObject, headers, status);
    }

    // Database constraint
    // Handle Spring's built-in database integrity violation exception.
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex,
            WebRequest request) {

        ErrorObject errorObject = new ErrorObject(
                HttpStatus.CONFLICT.value(),
                "The operation violates a database constraint.",
                new Date(),
                "DATA_INTEGRITY_VIOLATION",
                getRequestPath(request)
        );

        return new ResponseEntity<>(errorObject, HttpStatus.CONFLICT);
    }

    // Handle unexpected exceptions.
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGeneralException(
            Exception ex,
            WebRequest request) {

        logger.error("An unexpected server error occurred.", ex);

        ErrorObject errorObject = new ErrorObject(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected server error occurred.",
                new Date(),
                "INTERNAL_SERVER_ERROR",
                getRequestPath(request)
        );

        return new ResponseEntity<>(
                errorObject, HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}