package com.soccer.matchUp.dto.request;

import com.soccer.matchUp.entity.Team;
import com.soccer.matchUp.entity.TeamMember;

public record TeamMemberRequest(
        Long id,
        String name,
        Integer number,
        String position
) {
    public static TeamMemberRequest of(
            Long id,
            String name,
            Integer number,
            String position
    ){
        return new TeamMemberRequest(id, name, number, position);
    }

    public TeamMember toEntity(Team team) {
        return TeamMember.builder()
                .name(name())
                .number(number())
                .position(position())
                .team(team)
                .build();
    }
}
