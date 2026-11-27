package com.qt.hospital_equipment_tracking.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.Map;


public class ErrorObject {

    private Integer statusCode;
    private String message;
    private Date timestamp;
    private String errorCode;
    private String path;

    // Field-level validation errors.
    // Omitted from the JSON response when null.
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> fieldErrors;

    public ErrorObject() {
    }

    public ErrorObject(Integer statusCode, String message,
                         Date timestamp, String errorCode,
                         String path) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = timestamp;
        this.errorCode = errorCode;
        this.path = path;
    }

    public ErrorObject(Integer statusCode, String message,
                         Date timestamp, String errorCode,
                         String path, Map<String, String> fieldErrors) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = timestamp;
        this.errorCode = errorCode;
        this.path = path;
        this.fieldErrors = fieldErrors;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}