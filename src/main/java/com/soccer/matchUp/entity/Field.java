package com.soccer.matchUp.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Field extends BaseTime{

    @Id
    private Long svcId;

    @Column(nullable = false)
    private String svcStatNm;
    @Column(nullable = false)
    private String svcNm;
    @Column(nullable = false)
    private String payAtNm;
    @Column(nullable = false)
    private String placeNm;
    @Column(nullable = false)
    private String svcUrl;
    @Column(nullable = false)
    private LocalDateTime rcptBgnDt;
    @Column(nullable = false)
    private LocalDateTime rcptEndDt;
    @Column(nullable = false)
    private String areaNm;
    @Column(nullable = false)
    private String imgUrl;
    @Column(nullable = true)
    private String dtlCont;
    @Column(nullable = false)
    private String telNo;
    @Column(nullable = false)
    private String revStdDayNm;
    @Column(nullable = false)
    private String revStdDay;
    @Column(nullable = false)
    private LocalDateTime vMin;
    @Column(nullable = false)
    private LocalDateTime vMax;
    @Column(nullable = false)
    private LocalDateTime svcOpnBgnDt;
    @Column(nullable = false)
    private LocalDateTime svcOpnEndDt;

    @Builder
    public Field(String svcStatNm, String svcNm, String payAtNm, String placeNm, String svcUrl, LocalDateTime rcptBgnDt, LocalDateTime rcptEndDt, String areaNm, String imgUrl, String dtlCont, String telNo, String revStdDayNm, String revStdDay, LocalDateTime vMin, LocalDateTime vMax, LocalDateTime svcOpnBgnDt, LocalDateTime svcOpnEndDt) {
        this.svcStatNm = svcStatNm;
        this.svcNm = svcNm;
        this.payAtNm = payAtNm;
        this.placeNm = placeNm;
        this.svcUrl = svcUrl;
        this.rcptBgnDt = rcptBgnDt;
        this.rcptEndDt = rcptEndDt;
        this.areaNm = areaNm;
        this.imgUrl = imgUrl;
        this.dtlCont = dtlCont;
        this.telNo = telNo;
        this.revStdDayNm = revStdDayNm;
        this.revStdDay = revStdDay;
        this.vMin = vMin;
        this.vMax = vMax;
        this.svcOpnBgnDt = svcOpnBgnDt;
        this.svcOpnEndDt = svcOpnEndDt;
    }
}
