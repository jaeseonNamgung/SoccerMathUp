package com.soccer.matchUp.dto.response;

import com.soccer.matchUp.entity.TeamMember;

public record TeamMemberResponse(
        Long id,
        String name,
        Integer number,
        String position
) {
    public static TeamMemberResponse of(
            Long id,
            String name,
            Integer number,
            String position
    ){
        return new TeamMemberResponse(id, name, number, position);
    }

    public static TeamMemberResponse fromResponse(TeamMember teamMember){
        return TeamMemberResponse.of(
                teamMember.getId(),
                teamMember.getName(),
                teamMember.getNumber(),
                teamMember.getPosition()
        );
    }

}
