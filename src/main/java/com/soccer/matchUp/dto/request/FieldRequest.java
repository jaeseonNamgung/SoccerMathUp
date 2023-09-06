package com.soccer.matchUp.dto.request;

import java.time.LocalDateTime;

public record FieldRequest(
        Long id,
        String svcStatNm,
        String svcNm,
        String payAtNm,
        String placeNm,
        String svcUrl,
        LocalDateTime rcptBgnDt,
        LocalDateTime rcptEndDt,
        String areaNm,
        String imgUrl,
        String dtlCont,
        String telNo,
        String revStdDayNm,
        String revStdDay,
        LocalDateTime vMin,
        LocalDateTime vMax,
        LocalDateTime svcOpnBgnDt,
        LocalDateTime svcOpnEndDt
) {

    public static FieldRequest of(
            Long id,
            String svcStatNm,
            String svcNm,
            String payAtNm,
            String placeNm,
            String svcUrl,
            LocalDateTime rcptBgnDt,
            LocalDateTime rcptEndDt,
            String areaNm,
            String imgUrl,
            String dtlCont,
            String telNo,
            String revStdDayNm,
            String revStdDay,
            LocalDateTime vMin,
            LocalDateTime vMax,
            LocalDateTime svcOpnBgnDt,
            LocalDateTime svcOpnEndDt
    ){
        return new FieldRequest(id, svcStatNm, svcNm, payAtNm, placeNm,
                svcUrl, rcptBgnDt, rcptEndDt, areaNm,
                imgUrl, dtlCont,telNo, revStdDayNm, revStdDay,
                vMin, vMax, svcOpnBgnDt, svcOpnEndDt);
    }
}
