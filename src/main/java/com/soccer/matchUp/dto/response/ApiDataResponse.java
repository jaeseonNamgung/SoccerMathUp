package com.soccer.matchUp.dto.response;

import com.soccer.matchUp.error.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.BindingResult;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
public class ApiDataResponse<T> extends ApiErrorResponse{
    private final T data;

    public ApiDataResponse(T data) {
        super(true, ErrorCode.OK.getHttpStatus().value(), ErrorCode.OK.getMessage());
        this.data = data;
    }

    public static <T> ApiDataResponse<T> of(T data){
        return new ApiDataResponse<>(data);
    }

}
