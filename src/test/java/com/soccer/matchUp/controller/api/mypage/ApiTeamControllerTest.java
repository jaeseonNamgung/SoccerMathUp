package com.soccer.matchUp.controller.api.mypage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soccer.matchUp.dto.request.TeamRequest;
import com.soccer.matchUp.dto.response.FieldException;
import com.soccer.matchUp.dto.response.TeamResponse;
import com.soccer.matchUp.error.ErrorCode;
import com.soccer.matchUp.service.TeamService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(ApiTeamController.class)
class ApiTeamControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private TeamService teamService;



    @DisplayName("[API][POST][SUCCESS] 팀 생성 테스트")
    @Test
    public void createTeamTest()throws Exception{
        // given
        Long memberId = 1L;
        TeamRequest teamRequest = createTeamRequest("홍길동", 19);
        given(teamService.addTeam(memberId, teamRequest)).willReturn(true);
        // when & then
        mvc.perform(post("/mypage/team/" + memberId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(teamRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data").value(true))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.status").value(ErrorCode.OK.getHttpStatus().value()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));

        then(teamService).should().addTeam(memberId, teamRequest);

    }

    @DisplayName("[API][POST][VALIDATION ERROR] 팀 생성 - 잘못된 값 입력")
    @MethodSource
    @ParameterizedTest
    public void createTeamValidationErrorTest(TeamRequest teamRequest, FieldException fieldException)throws Exception{
        // given
        Long memberId = 1L;
        // when & then
        mvc.perform(post("/mypage/team/" + memberId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(teamRequest)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.status").value(ErrorCode.VALIDATION_ERROR.getHttpStatus().value()))
                .andExpect(jsonPath("$.message").value(ErrorCode.VALIDATION_ERROR.getMessage()))
                .andExpect(jsonPath("$.exceptions[0].field").value(fieldException.field()))
                .andExpect(jsonPath("$.exceptions[0].value").value(fieldException.value()))
                .andExpect(jsonPath("$.exceptions[0].reason").value(fieldException.reason()));
    }

    @DisplayName("[API][GET] 팀 조회 성공 테스트")
    @Test
    public void getTeamTest()throws Exception{
        // given
        Long id = 1L;
        TeamResponse teamResponse = getTeamResponse(id);
        given(teamService.getTeam(1L)).willReturn(teamResponse);
        // when & then
        mvc.perform(get("/mypage/team/"+id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.name").value(teamResponse.name()))
                .andExpect(jsonPath("$.data.logo").value(teamResponse.logo()))
                .andExpect(jsonPath("$.data.uniformTop").value(teamResponse.uniformTop()))
                .andExpect(jsonPath("$.data.ageMoreThan").value(teamResponse.ageMoreThan()));
        then(teamService).should().getTeam(id);
    }

    @DisplayName("[API][GET] 팀 정보가 존재 하지 않을 경우, 빈 값을 리턴")
    @Test
    public void getTeamNoDataTest()throws Exception{
        // given
        Long id = 1L;
        given(teamService.getTeam(id)).willReturn(null);
        // when & then
        mvc.perform(get("/mypage/team/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data").isEmpty());
        then(teamService).should().getTeam(id);
    }

    @DisplayName("[API][PUT] 팀 수정 성공 테스트")
    @Test
    public void modifyTeamTest()throws Exception{
        // given
        Long id = 1L;
        TeamRequest teamRequest = createTeamRequest("홍길동", 19);
        TeamRequest modifyTeamRequest = createTeamRequest("김길동", 25);
        TeamResponse teamResponse = getTeamResponse(id);
        given(teamService.addTeam(id, teamRequest)).willReturn(true);
        given(teamService.modifyTeam(id, modifyTeamRequest)).willReturn(teamResponse);
        // when & then
        mvc.perform(put("/mypage/team/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(modifyTeamRequest)))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.name").value(teamResponse.name()))
                .andExpect(jsonPath("$.data.logo").value(teamResponse.logo()))
                .andExpect(jsonPath("$.data.uniformTop").value(teamResponse.uniformTop()))
                .andExpect(jsonPath("$.data.ageMoreThan").value(teamResponse.ageMoreThan()));

        then(teamService).should().addTeam(id, teamRequest);
        then(teamService).should().modifyTeam(id, modifyTeamRequest);

    }


    static Stream<Arguments> createTeamValidationErrorTest() throws Exception {
        return Stream.of(
                Arguments.arguments(createTeamRequest(" ", 20), FieldException.of("name", " ", "이름은 필수 값입니다.")),
                Arguments.arguments(createTeamRequest("홍길동", -20), FieldException.of("ageMoreThan", "-20", "양수 값만 입력해주세요"))
        );
    }

    private static TeamRequest createTeamRequest(String name, Integer ageMoreThan) {
        return TeamRequest.of(
                1L,
                name
                ,"logo",
                "top",
                "pants",
                ageMoreThan,
                30,
                "소개합니다."
        );
    }

    private static TeamResponse getTeamResponse(Long id) {
        return TeamResponse.of(
                id, "김길동", "logo", "top", "pants",
                25, 30, "소개합니다.",null
        );
    }

}