package com.soccer.matchUp.error;

import lombok.Getter;


@Getter
public class BaseException extends RuntimeException{

    private final ErrorCode errorCode;

    public BaseException() {
        super(ErrorCode.DATA_ACCESS_ERROR.getMessage());
        this.errorCode = ErrorCode.DATA_ACCESS_ERROR;
    }

    public BaseException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BaseException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}
