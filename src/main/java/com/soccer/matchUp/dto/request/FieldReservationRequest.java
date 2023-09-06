package com.soccer.matchUp.dto.request;

import java.time.LocalDateTime;

public record FieldReservationRequest(
        Long id,
        LocalDateTime reservationDate
) {
    public static FieldReservationRequest of(
            Long id,
            LocalDateTime reservationDate
    ){
        return new FieldReservationRequest(id, reservationDate);
    }
}
