package com.soccer.matchUp.dto.response;

public record FieldException(
        String field,
        String value,
        String reason
) {

    public static FieldException of(
            String field,
            String value,
            String reason
    ){
        return new FieldException(field, value, reason);
    }
}
