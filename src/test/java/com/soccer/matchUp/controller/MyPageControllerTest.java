package com.soccer.matchUp.controller;


import com.soccer.matchUp.config.JpaQueryFactoryConfig;
import com.soccer.matchUp.dto.response.TeamMemberResponse;
import com.soccer.matchUp.dto.response.TeamResponse;
import com.soccer.matchUp.service.TeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MyPageController.class)
@MockBean(JpaMetamodelMappingContext.class)
class MyPageControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private TeamService teamService;

    @Test
    public void showGetTeamSuccessTest()throws Exception{
        // given
        given(teamService.getTeam(anyLong())).willReturn(createTeamResponse());
        // when&then
        mvc.perform(get("/mypage/getTeam/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("mypage/team/index"))
                .andExpect(model().attributeExists("team"));
        then(teamService).should(times(1)).getTeam(anyLong());
    }

    private TeamResponse createTeamResponse() {
        return TeamResponse.of(
                1L,
                "team",
                "logo",
                "top",
                "pants",
                19,
                30,
                "소개",
                List.of(
                        TeamMemberResponse.of(1L, "홍길동",10, "MF" )
                )
        );
    }


}