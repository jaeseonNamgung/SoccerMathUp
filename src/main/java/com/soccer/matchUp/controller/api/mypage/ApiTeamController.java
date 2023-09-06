package com.soccer.matchUp.controller.api.mypage;

import com.soccer.matchUp.dto.request.TeamRequest;
import com.soccer.matchUp.dto.response.ApiDataResponse;
import com.soccer.matchUp.dto.response.TeamResponse;
import com.soccer.matchUp.entity.Team;
import com.soccer.matchUp.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
public class ApiTeamController {
    private final TeamService teamService;

    @PostMapping("/mypage/team/{id}")
    public ApiDataResponse<Boolean> createTeam(@PathVariable(required = false,name = "id") Long memberId,
                                               @Valid @RequestBody TeamRequest teamRequest){
        return ApiDataResponse.of(teamService.addTeam(memberId, teamRequest));
    }

    @GetMapping("/mypage/team/{id}")
    public ApiDataResponse<TeamResponse> getTeam(@PathVariable(name = "id") Long memberId){
        return ApiDataResponse.of(teamService.getTeam(memberId));
    }

    @PutMapping("/mypage/team/{id}")
    public ApiDataResponse<TeamResponse> modifyTeam(@PathVariable(name = "id")Long memberId,
                                                    @Valid @RequestBody TeamRequest teamRequest){
        return ApiDataResponse.of(teamService.modifyTeam(memberId, teamRequest));
    }


}
