package com.soccer.matchUp.dto.response;

import com.soccer.matchUp.entity.Team;
import com.soccer.matchUp.entity.TeamMember;

import java.util.List;
import java.util.stream.Collectors;

public record TeamResponse(
        Long id,
        String name,
        String logo,
        String uniformTop,
        String uniformPants,
        Integer ageMoreThan,
        Integer ageLessThan,
        String introduction,
        List<TeamMemberResponse> teamMemberList
) {

    public static TeamResponse of(
            Long id,
            String name,
            String logo,
            String uniformTop,
            String uniformPants,
            Integer ageMoreThan,
            Integer ageLessThan,
            String introduction,
            List<TeamMemberResponse> teamMemberList
    ){
        return new TeamResponse(id, name, logo, uniformTop, uniformPants, ageMoreThan, ageLessThan, introduction, teamMemberList);
    }


    public static TeamResponse fromResponse(Team team) {
        return TeamResponse.of(
                team.getId(),
                team.getName(),
                team.getLogo(),
                team.getUniformTop(),
                team.getUniformPants(),
                team.getAgeMoreThan(),
                team.getAgeLessThan(),
                team.getIntroduction(),
                team.getTeamMembers().stream().map(TeamMemberResponse::fromResponse).collect(Collectors.toList())
        );
    }

    public static TeamResponse empty() {
        return TeamResponse.of(null,null,null,null,null,null,
                null,null,null);
    }
}
