package com.soccer.matchUp.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Result extends BaseTime{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String score;
    private String details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;



    @Builder
    public Result(String score, String details, Match match) {
        this.score = score;
        this.details = details;
        this.match = match;
    }
}
