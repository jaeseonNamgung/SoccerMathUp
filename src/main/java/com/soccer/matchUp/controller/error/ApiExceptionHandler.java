package com.soccer.matchUp.controller.error;

import com.soccer.matchUp.dto.response.ApiErrorResponse;
import com.soccer.matchUp.dto.response.ApiValidationErrorResponse;
import com.soccer.matchUp.error.ErrorCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiValidationErrorResponse response =
                ApiValidationErrorResponse.createBindingResult(ErrorCode.VALIDATION_ERROR, ex.getBindingResult());
        return handleExceptionInternal(ex,response, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, ErrorCode.valueOf(status) , headers, status, request);
    }

    protected  ResponseEntity<Object> handleExceptionInternal(Exception ex, ErrorCode errorCode, WebRequest request){
        return handleExceptionInternal(ex, errorCode, HttpHeaders.EMPTY, errorCode.getHttpStatus(), request);
    }
    protected  ResponseEntity<Object> handleExceptionInternal(Exception ex, ApiValidationErrorResponse response,HttpHeaders headers, HttpStatus httpStatus, WebRequest request){
        return super.handleExceptionInternal(ex, response,headers,httpStatus, request);
    }
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, ErrorCode errorCode, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal
                (ex, ApiErrorResponse.of(false, errorCode), headers, status, request);
    }
}
