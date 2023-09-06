package com.soccer.matchUp.entity;

import com.soccer.matchUp.dto.request.ScorerRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Scorer {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer number;
    private Integer goalCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matchingHistory_id")
    private MatchingHistory matchingHistory;

    @Builder
    public Scorer(String name, Integer number, Integer goalCount, MatchingHistory matchingHistory) {
        this.name = name;
        this.number = number;
        this.goalCount = goalCount;
        this.matchingHistory = matchingHistory;
    }

    public void setMatchingHistory(MatchingHistory mHistory){
        if(this.matchingHistory != null){
            this.matchingHistory.getScorers().remove(this);
        }
        this.matchingHistory = mHistory;
        mHistory.getScorers().add(this);
    }

    public void update(ScorerRequest scorerRequest) {
        if(this.name != null){this.name = scorerRequest.name();}
        if(this.number != null){this.number = scorerRequest.number();}
        if(this.goalCount != null){this.goalCount = scorerRequest.goalCount();}
    }
}
