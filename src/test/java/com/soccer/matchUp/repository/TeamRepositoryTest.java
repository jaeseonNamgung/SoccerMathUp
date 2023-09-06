package com.soccer.matchUp.repository;

import com.soccer.matchUp.entity.Team;
import com.soccer.matchUp.entity.TeamMember;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepo;
    @Autowired
    private EntityManager entityManager;

    @Test
    public void getTeamSucceedsWithTeamIdTest()throws Exception{
        // given
        Team team = Team.builder()
                .name("team")
                .uniformTop("top")
                .uniformPants("pants")
                .logo("logo")
                .ageMoreThan(19)
                .ageLessThan(30)
                .introduction("소개합니다.")
                .build();

        TeamMember teamMember = TeamMember.builder()
                .name("홍길동")
                .number(10)
                .position("MF")
                .build();
        teamMember.setTeam(team);
        entityManager.persist(team);

        // when
        Team result = teamRepo.findById(1L).get();
        // then
        assertThat(result)
                .extracting("name","uniformTop", "uniformPants", "logo",
                        "ageMoreThan", "ageLessThan","introduction")
                .containsExactly(team.getName(),team.getUniformTop(), team.getUniformPants(),
                        team.getLogo(), team.getAgeMoreThan(), team.getAgeLessThan(), team.getIntroduction());

        assertThat(result.getTeamMembers().get(0))
                .hasFieldOrPropertyWithValue("name", teamMember.getName())
                .hasFieldOrPropertyWithValue("number", teamMember.getNumber())
                .hasFieldOrPropertyWithValue("position", teamMember.getPosition());

    }


}