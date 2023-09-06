package com.soccer.matchUp.repository;

import com.querydsl.apt.jpa.JPAConfiguration;
import com.soccer.matchUp.config.JpaQueryFactoryConfig;
import com.soccer.matchUp.entity.Team;
import com.soccer.matchUp.entity.TeamMember;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(JpaQueryFactoryConfig.class)
@DataJpaTest
class TeamMemberRepositoryTest {

    @Autowired
    private TeamMemberRepository teamMemberRepo;
    @Autowired
    private TeamRepository teamRepo;

    @Test
    public void findAllByOrderByNameAscTest()throws Exception{
        // given
        teamRepo.save(createTeam());
        Team team = teamRepo.findAll().get(0);
        teamMemberRepo.save(createTeamMember("홍길동", 10, "MF", team));
        teamMemberRepo.save(createTeamMember("김길동", 9, "FW", team));
        // when
        List<TeamMember> result = teamMemberRepo.findAllByOrderByNameAsc();
        // then
        assertThat(result.get(0))
                .hasFieldOrPropertyWithValue("name", "김길동")
                .hasFieldOrPropertyWithValue("number", 9)
                .hasFieldOrPropertyWithValue("position", "FW");
    }

    private static Team createTeam() {
        return Team.builder()
                .name("team")
                .uniformTop("top")
                .uniformPants("pants")
                .ageLessThan(19)
                .ageMoreThan(30)
                .introduction("소개합니다")
                .build();
    }

    private static TeamMember createTeamMember(String name, Integer number, String position, Team team){
        return TeamMember.builder()
                .name(name)
                .number(number)
                .position(position)
                .team(team)
                .build();
    }

}