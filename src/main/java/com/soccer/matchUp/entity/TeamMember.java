package com.soccer.matchUp.entity;

import com.soccer.matchUp.dto.request.TeamMemberRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class TeamMember extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer number;
    private String position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_history_id")
    private MatchingHistory matchingHistory;

    @Builder
    public TeamMember(String name, Integer number, String position,MatchingHistory matchingHistory, Team team) {
        this.name = name;
        this.number = number;
        this.position = position;
        this.matchingHistory = matchingHistory;
        this.setTeam(team);
    }

    public void setTeam(Team team){
        if(this.team != null){
            this.team.getTeamMembers().remove(this);
        }
        this.team = team;
        team.getTeamMembers().add(this);
    }

    public void update(TeamMemberRequest teamMemberRequest) {
        if(teamMemberRequest.name() != null) this.name = teamMemberRequest.name();
        if(teamMemberRequest.number() != null) this.number = teamMemberRequest.number();
        if(teamMemberRequest.position() != null) this.position = teamMemberRequest.position();
    }
}
