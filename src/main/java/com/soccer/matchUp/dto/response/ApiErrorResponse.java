package com.soccer.matchUp.dto.response;


import com.soccer.matchUp.error.ErrorCode;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode
@RequiredArgsConstructor
public class ApiErrorResponse {

    private final Boolean success;
    private final Integer status;
    private final String message;

    public static ApiErrorResponse of(Boolean success, Integer status, String message){
        return new ApiErrorResponse(success, status, message);
    }

    public static ApiErrorResponse of(Boolean success, ErrorCode errorCode){
        return new ApiErrorResponse(success, errorCode.getHttpStatus().value() , errorCode.getMessage());
    }

}
