package com.soccer.matchUp.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
}
