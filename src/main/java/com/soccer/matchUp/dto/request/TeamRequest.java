package com.soccer.matchUp.dto.request;

import com.soccer.matchUp.entity.Team;
import com.soccer.matchUp.entity.TeamMember;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

public record TeamRequest(
        Long id,
        @NotBlank(message = "이름은 필수 값입니다.")
        String name,
        String logo,
        String uniformTop,
        String uniformPants,
        @NotNull(message = "나이는 필수 값입니다.")
        @Positive(message = "양수 값만 입력해주세요")
        Integer ageMoreThan,
        @NotNull(message = "나이는 필수 값입니다.")
        @Positive(message = "양수 값만 입력해주세요")
        Integer ageLessThan,
        String introduction

) {
    public static TeamRequest of(
            Long id,
            String name,
            String logo,
            String uniformTop,
            String uniformPants,
            Integer ageMoreThan,
            Integer ageLessThan,
            String introduction
    ){
        return new TeamRequest(id, name, logo, uniformTop, uniformPants, ageMoreThan, ageLessThan, introduction);
    }

    public Team toEntity() {
        return Team.builder()
                .name(name())
                .logo(logo())
                .uniformTop(uniformTop())
                .uniformPants(uniformPants())
                .ageMoreThan(ageMoreThan())
                .ageLessThan(ageLessThan())
                .introduction(introduction())
                .build();
    }
}
