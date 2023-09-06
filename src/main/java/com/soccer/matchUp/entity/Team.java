package com.soccer.matchUp.entity;

import com.soccer.matchUp.dto.request.TeamRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Team extends BaseTime{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String uniformTop;
    private String uniformPants;
    
    // default 설정 필요
    private String logo;
    private Integer ageMoreThan;
    private Integer ageLessThan;
    private String introduction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "team", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<TeamMember> teamMembers = new ArrayList<>();

    @Builder
    public Team(String name, String uniformTop, String uniformPants, String logo, Integer ageMoreThan, Integer ageLessThan, String introduction) {
        this.name = name;
        this.uniformTop = uniformTop;
        this.uniformPants = uniformPants;
        this.logo = logo;
        this.ageMoreThan = ageMoreThan;
        this.ageLessThan = ageLessThan;
        this.introduction = introduction;
    }

    public void update(TeamRequest teamRequest) {
        if(hasText(teamRequest.name())) this.name = teamRequest.name();
        if(hasText(teamRequest.uniformTop())) this.uniformTop = teamRequest.uniformTop();
        if(hasText(teamRequest.uniformPants())) this.uniformPants = teamRequest.uniformPants();
        if(hasText(teamRequest.logo())) this.logo = teamRequest.logo();
        if(hasText(teamRequest.ageMoreThan().toString())) this.ageMoreThan = teamRequest.ageMoreThan();
        if(hasText(teamRequest.ageLessThan().toString())) this.ageLessThan = teamRequest.ageLessThan();
        if(hasText(teamRequest.introduction())) this.introduction = teamRequest.introduction();
    }

    public void addMember(Member member){
        this.member = member;
    }
}
