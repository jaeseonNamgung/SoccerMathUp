package com.soccer.matchUp.controller;

import com.soccer.matchUp.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Validated
@Controller
public class MyPageController {

    private final TeamService teamService;

    @GetMapping("/mypage/getTeam/{id}")
    public String showGetTeam(Model model, @PathVariable Long id){
        model.addAttribute("team", teamService.getTeam(id));
        return "mypage/team/index";
    }

}
