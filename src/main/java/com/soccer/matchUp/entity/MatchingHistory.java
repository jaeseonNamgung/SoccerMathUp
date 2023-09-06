package com.soccer.matchUp.entity;

import com.soccer.matchUp.dto.request.MatchingHistoryRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class MatchingHistory extends BaseTime{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String homeTeamName;
    @Column(nullable = false)
    private String awayTeamName;
    @Column(nullable = false)
    private String score;
    private String matchHistoryAgainst;
    private String detail;
    private LocalDateTime matchDate;

    @OneToMany(mappedBy = "matchingHistory", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Scorer> scorers = new ArrayList<>();

    @Builder
    public MatchingHistory(Long id, String homeTeamName, String awayTeamName, String score, String matchHistoryAgainst, String detail, LocalDateTime matchDate) {
        this.id = id;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.score = score;
        this.matchHistoryAgainst = matchHistoryAgainst;
        this.detail = detail;
        this.matchDate = matchDate;
    }

    public void update(MatchingHistoryRequest mHistoryRequest) {
        if(hasText(mHistoryRequest.homeTeamName())){this.homeTeamName = mHistoryRequest.homeTeamName();}
        if(hasText(mHistoryRequest.awayTeamName())){this.awayTeamName = mHistoryRequest.awayTeamName();}
        if(hasText(mHistoryRequest.score())){this.score = mHistoryRequest.score();}
        if(hasText(mHistoryRequest.matchHistoryAgainst())){this.matchHistoryAgainst = mHistoryRequest.matchHistoryAgainst();}
        if(hasText(mHistoryRequest.detail())){this.detail = mHistoryRequest.detail();}
        if(mHistoryRequest.matchDate() != null){this.matchDate = mHistoryRequest.matchDate();}
    }
}
