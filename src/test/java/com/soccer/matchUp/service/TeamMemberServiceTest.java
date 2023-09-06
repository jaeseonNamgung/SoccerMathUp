package com.soccer.matchUp.service;

import com.soccer.matchUp.dto.request.TeamMemberRequest;
import com.soccer.matchUp.dto.response.TeamMemberResponse;
import com.soccer.matchUp.entity.Team;
import com.soccer.matchUp.entity.TeamMember;
import com.soccer.matchUp.error.BaseException;
import com.soccer.matchUp.error.ErrorCode;
import com.soccer.matchUp.repository.TeamMemberRepository;
import com.soccer.matchUp.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TeamMemberServiceTest {

    @InjectMocks
    private TeamMemberService teamMemberService;
    @Mock
    private TeamRepository teamRepo;
    @Mock
    private TeamMemberRepository teamMemberRepo;

    @Test
    public void addTeamMemberSuccessTest()throws Exception{
        // given
        Team createTeam = createTeam();
        given(teamRepo.findById(anyLong())).willReturn(
                Optional.of(createTeam)
        );
        given(teamMemberRepo.save(any())).willReturn(any(TeamMember.class));
        // when

        boolean result = teamMemberService.addTeamMember(1L, createTeamMemberRequest());

        // then
        assertThat(result).isTrue();
        then(teamRepo).should().findById(anyLong());
        then(teamMemberRepo).should().save(any());
    }

    @Test
    public void addTeamMemberNullIdTest()throws Exception{
        // given

        // when
        Exception e = catchException(() -> teamMemberService.addTeamMember(null, createTeamMemberRequest()));
        // then
        assertThat(e).isInstanceOf(BaseException.class).hasMessageContaining("데이터가 널 값 입니다.");
    }
    @Test
    public void addTeamMemberNullTeamMemberRequestTest()throws Exception{
        // given

        // when
        Exception e = catchException(() -> teamMemberService.addTeamMember(1L, null));
        // then
        assertThat(e).isInstanceOf(BaseException.class).hasMessageContaining("데이터가 널 값 입니다.");
    }
    @Test
    public void addTeamMemberNullIdAndTeamMemberRequestTest()throws Exception{
        // given

        // when
        Exception e = catchException(() -> teamMemberService.addTeamMember(null, null));
        // then
        assertThat(e).isInstanceOf(BaseException.class).hasMessageContaining("데이터가 널 값 입니다.");
    }

    @Test
    public void addTeamMemberNotFoundTeamTest()throws Exception{
        // given
        given(teamRepo.findById(anyLong())).willReturn(Optional.empty());

        // when
        Exception e = catchException(() -> teamMemberService.addTeamMember(1L, createTeamMemberRequest()));

        // then
        assertThat(e)
                .isInstanceOf(BaseException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND_DATA.getMessage());
    }

    @Test
    public void getTeamMembersOrderByNameDescSuccessTest()throws Exception{
        // given
        given(teamMemberRepo.findAllByOrderByNameAsc())
                .willReturn(
                        Arrays.asList(
                                createTeamMember("김길동", 9, "FW"),
                                createTeamMember("홍길동", 10, "MF")
                        )
                );
        // when
        List<TeamMemberResponse> result = teamMemberService.getTeamMembers();
        // then
        assertThat(result.get(0))
                .extracting("name", "number","position")
                .containsExactly("김길동", 9, "FW");
        then(teamMemberRepo).should().findAllByOrderByNameAsc();
    }
    
    @Test
    public void modifyTeamMemberSuccessTest()throws Exception{
        // given
        TeamMemberRequest modifyTeamMemberRequest = modifyTeamMemberRequest("김길동", 9, "FW");
        TeamMember teamMember = createTeamMember("홍길동", 10, "MF");
        given(teamMemberRepo.findById(anyLong())).willReturn(Optional.of(teamMember));
        // when
        teamMemberService.modifyTeamMember(1L, modifyTeamMemberRequest);
        // then
        assertThat(teamMember.getName()).isEqualTo(modifyTeamMemberRequest.name());
        assertThat(teamMember.getNumber()).isEqualTo(modifyTeamMemberRequest.number());
        assertThat(teamMember.getPosition()).isEqualTo(modifyTeamMemberRequest.position());

        then(teamMemberRepo).should().findById(anyLong());
    }

    @Test
    public void modifyTeamMemberWithNullIdAndNullTeamMemberRequestTest()throws Exception{
        // given
        TeamMemberRequest modifyTeamMemberRequest = modifyTeamMemberRequest("김길동", 9, "FW");
        // when
        Exception e = catchException(() -> teamMemberService.modifyTeamMember(null, modifyTeamMemberRequest));
        // then
        assertThat(e)
                .isInstanceOf(BaseException.class)
                .hasMessageContaining(ErrorCode.NO_DATA.getMessage());
    }
    @Test
    public void modifyTeamMemberWithNullIdTest()throws Exception{
        // given
        // when
        Exception e = catchException(() -> teamMemberService.modifyTeamMember(null, null));
        // then
        assertThat(e)
                .isInstanceOf(BaseException.class)
                .hasMessageContaining(ErrorCode.NO_DATA.getMessage());
    }
    @Test
    public void modifyTeamMemberWithNullTeamMemberRequestTest()throws Exception{
        // given
        // when
        Exception e = catchException(() -> teamMemberService.modifyTeamMember(1L, null));
        // then
        assertThat(e)
                .isInstanceOf(BaseException.class)
                .hasMessageContaining(ErrorCode.NO_DATA.getMessage());
    }

    @Test
    public void removeTeamMemberSuccessTest()throws Exception{
        // given
        Long id = 1L;
        willDoNothing().given(teamMemberRepo).deleteById(id);
        // when
        teamMemberService.removeTeamMember(id);
        // then
        assertThat(teamMemberRepo.findById(id)).isEmpty();
        then(teamMemberRepo).should().deleteById(id);
    }

    @Test
    public void removeTeamMemberWithNullIdTest()throws Exception{
        // given

        // when
        Exception e = catchException(() -> teamMemberService.removeTeamMember(null));
        // then
        assertThat(e).isInstanceOf(BaseException.class)
                .hasMessageContaining(ErrorCode.NO_DATA.getMessage());
    }




    private static TeamMemberRequest createTeamMemberRequest(){
        return TeamMemberRequest.of(
                null,
                "홍길동",
                10,
                "MF"
        );
    }
    private static TeamMemberRequest modifyTeamMemberRequest(String name, Integer number, String position){
        return TeamMemberRequest.of(
                null,
                name,
                number,
                position
        );
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

    private static TeamMember createTeamMember(String name, Integer number, String position){
        return TeamMember.builder()
                .name(name)
                .number(number)
                .position(position)
                .team(createTeam())
                .build();
    }

}