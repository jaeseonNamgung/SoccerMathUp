package com.soccer.matchUp.dto.response;


import com.soccer.matchUp.error.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@SuperBuilder
public class ApiValidationErrorResponse extends ApiErrorResponse {

    private final List<FieldException> exceptions;


    public static ApiValidationErrorResponse createBindingResult(ErrorCode errorCode, BindingResult bindingResult){
        return ApiValidationErrorResponse.builder()
                .success(false)
                .status(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage())
                .exceptions(create(bindingResult))
                .build();
    }

    private static List<FieldException> create(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return fieldErrors == null ? null :
                fieldErrors.stream()
                        .map(error ->
                             FieldException.of(error.getField(),
                                    Optional.ofNullable(error.getRejectedValue().toString())
                                            .orElse(null),
                                    error.getDefaultMessage())
                        )
                        .collect(Collectors.toList());

    }
}
