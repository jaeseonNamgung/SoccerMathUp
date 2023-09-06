package com.soccer.matchUp.service;

import com.soccer.matchUp.dto.request.TeamRequest;
import com.soccer.matchUp.dto.response.TeamMemberResponse;
import com.soccer.matchUp.dto.response.TeamResponse;
import com.soccer.matchUp.entity.Member;
import com.soccer.matchUp.entity.Team;
import com.soccer.matchUp.error.BaseException;
import com.soccer.matchUp.error.ErrorCode;
import com.soccer.matchUp.repository.MemberRepository;
import com.soccer.matchUp.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @InjectMocks
    private TeamService teamService;
    @Mock
    private TeamRepository teamRepo;
    @Mock
    private MemberRepository memberRepo;

    @Test
    public void addTeamSuccessTest()throws Exception{
        // given
        Member member = createMember();
        given(memberRepo.findById(anyLong())).willReturn(
                Optional.of(member));
        given(teamRepo.save(any())).willReturn(any());

        // when
        TeamRequest teamRequest = createTeamRequest();
        boolean result = teamService.addTeam(1L, teamRequest);
        // then
        assertThat(result).isTrue();
        then(memberRepo).should().findById(anyLong());
        then(teamRepo).should().save(any());

    }

    private static Member createMember() {
        return Member.builder()
                .name("홍길동")
                .age(20)
                .build();
    }

    @Test
    public void addTeamUserNotFoundExceptionTest()throws Exception{
        // given
        given(memberRepo.findById(any())).willReturn(Optional.empty());
        // when
        TeamRequest teamRequest = createTeamRequest();
        Exception e = catchException(() -> teamService.addTeam(1L, teamRequest));
        // then
        assertThat(e)
                .isInstanceOf(BaseException.class)
                .hasMessageContaining(ErrorCode.USER_NOT_FOUND.getMessage());
        then(memberRepo).should().findById(any());
    }

    @Test
    public void addTeamWithNullMemberIdAndTeamRequestTest()throws Exception{
        // given
        // when
        Exception e = catchException(() -> teamService.addTeam(null, null));
        // then
        assertThat(e).isInstanceOf(BaseException.class)
                .hasMessageContaining("데이터가 널 값 입니다.");
    }

    @Test
    public void addTeamWithNullTeamRequestTest()throws Exception{
        // given
        // when
        Exception e = catchException(() -> teamService.addTeam(1L, null));
        // then
        assertThat(e).isInstanceOf(BaseException.class)
                .hasMessageContaining("데이터가 널 값 입니다.");
    }

    @Test
    public void getTeamSuccessTest()throws Exception{
        // given
        given(teamRepo.findById(anyLong())).willReturn(
                Optional.of(createTeam())
        );
        // when
        TeamResponse result = teamService.getTeam(1L);
        // then
        assertThat(result)
                .hasFieldOrPropertyWithValue("name","홍길동")
                .hasFieldOrPropertyWithValue("uniformTop", "top")
                .hasFieldOrPropertyWithValue("uniformPants", "pants");
        then(teamRepo).should(times(1)).findById(anyLong());
    }
    @Test
    public void getTeamWithNullIdTest()throws Exception{
        // given

        // when
        TeamResponse result = teamService.getTeam(null);
        // then
        assertThat(result).hasAllNullFieldsOrProperties();
    }

    @Test
    public void modifyTeamSuccessTest()throws Exception{
        // given
        given(teamRepo.findById(anyLong())).willReturn(
                Optional.of(createTeam())
        );
        // when
        TeamRequest updateTeamRequest = updateTeamRequest("김길동", 20, 50);
        TeamResponse result = teamService.modifyTeam(updateTeamRequest.id(), updateTeamRequest);
        // then
        assertThat(result)
                .hasFieldOrPropertyWithValue("name", updateTeamRequest.name())
                .hasFieldOrPropertyWithValue("ageLessThan", updateTeamRequest.ageLessThan())
                .hasFieldOrPropertyWithValue("ageMoreThan", updateTeamRequest.ageMoreThan());
        then(teamRepo).should().findById(anyLong());
    }

    @Test
    public void modifyTeamWithNullIdTest()throws Exception{
        // given

        // when
        TeamRequest updateTeamRequest = updateTeamRequest("김길동", 20, 50);
        Exception e = catchException(() -> teamService.modifyTeam(null, updateTeamRequest));
        // then
        assertThat(e).isInstanceOf(BaseException.class)
                .hasMessageContaining("데이터가 널 값 입니다.");
    }
    @Test
    public void modifyTeamWithNullTeamRequestTest()throws Exception{
        // given
        given(teamRepo.findById(anyLong())).willReturn(Optional.empty());
        // when
        TeamRequest updateTeamRequest = updateTeamRequest("김길동", 20, 50);
        Exception e = catchException(() -> teamService.modifyTeam(updateTeamRequest.id(), updateTeamRequest));
        // then
        assertThat(e).isInstanceOf(BaseException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND_DATA.getMessage());
    }
    @Test
    public void modifyTeamWithNullIdAndTeamRequestTest()throws Exception{
        // given

        // when
        Exception e = catchException(() -> teamService.modifyTeam(null, null));
        // then
        assertThat(e).isInstanceOf(BaseException.class)
                .hasMessageContaining("데이터가 널 값 입니다.");
    }

    @Test
    public void modifyTeamNotFoundTeamTest()throws Exception{
        // given

        // when
        Exception e = catchException(() -> teamService.modifyTeam(null, null));
        // then
        assertThat(e).isInstanceOf(BaseException.class)
                .hasMessageContaining("데이터가 널 값 입니다.");
    }

    @Test
    public void removeTeamSuccessTest()throws Exception{
        // given
        willDoNothing().given(teamRepo).deleteById(anyLong());
        // when
        boolean result = teamService.removeTeam(1L);
        // then
        assertThat(result).isTrue();
        then(teamRepo).should().deleteById(anyLong());
    }
    @Test
    public void removeTeamWithNullIdTest()throws Exception{
        // given
        // when
        boolean result = teamService.removeTeam(null);
        // then
        assertThat(result).isFalse();
    }


    private TeamRequest updateTeamRequest(String name, int ageLessThan, int ageMoreThan){
        return TeamRequest.of(
                1L,
                name,
                "logo",
                "top",
                "pants",
                ageLessThan,
                ageMoreThan,
                "소개합니다."
        );
    }

    private static Team createTeam() {
        return Team.builder()
                .name("홍길동")
                .uniformTop("top")
                .uniformPants("pants")
                .ageLessThan(19)
                .ageMoreThan(30)
                .introduction("소개합니다")
                .build();
    }


    private static TeamRequest createTeamRequest() {
        return TeamRequest.of(
                1L,
                "team",
                "logo",
                "top",
                "pants",
                19,
                30,
                "소개합니다."
        );
    }

    private static TeamResponse createTeamResponse() {
        return TeamResponse.of(
                1L,
                "team",
                "logo",
                "top",
                "pants",
                19,
                30,
                "소개합니다.",
                new ArrayList<>(
                        Arrays.asList(
                                createTeamMemberResponse(1L, "홍길동", 10, "FW"),
                                createTeamMemberResponse(2L, "김길동", 7, "MF")
                        )
                )
        );
    }

    private static TeamMemberResponse createTeamMemberResponse(Long id, String name, Integer number, String position){
        return TeamMemberResponse.of(id, name, number, position);
    }


}