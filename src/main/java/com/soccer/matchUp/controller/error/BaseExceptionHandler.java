package com.soccer.matchUp.controller.error;

import com.soccer.matchUp.error.BaseException;
import com.soccer.matchUp.error.ErrorCode;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public String exception(Model model, BaseException e){
        ErrorCode errorCode = e.getErrorCode();
        model.addAllAttributes(
                Map.of(
                        "status", errorCode.getHttpStatus(),
                        "message", errorCode.getMessage()
                )
        );
        return "error";
    }
}
