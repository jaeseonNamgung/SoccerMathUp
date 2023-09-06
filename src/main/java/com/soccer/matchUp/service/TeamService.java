package com.soccer.matchUp.service;

import com.soccer.matchUp.dto.request.TeamRequest;
import com.soccer.matchUp.dto.response.TeamResponse;
import com.soccer.matchUp.entity.Member;
import com.soccer.matchUp.entity.Team;
import com.soccer.matchUp.error.BaseException;
import com.soccer.matchUp.error.ErrorCode;
import com.soccer.matchUp.repository.MemberRepository;
import com.soccer.matchUp.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TeamService {
    private final TeamRepository teamRepo;
    private final MemberRepository memberRepo;

    @Transactional
    public boolean addTeam(Long memberId, TeamRequest teamRequest){
        if(teamRequest == null || memberId == null){
                throw new BaseException(ErrorCode.NO_DATA);
        }
        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
        Team team = teamRequest.toEntity();
        team.addMember(member);
        teamRepo.save(team);
        return true;
    }


    public TeamResponse getTeam(Long id){
        if(id == null){
            return TeamResponse.empty();
        }
        return TeamResponse.fromResponse(
                teamRepo.findById(id)
                        .orElseThrow(()->new BaseException(ErrorCode.NOT_FOUND_DATA)));
    }

    @Transactional
    public TeamResponse modifyTeam(Long id, TeamRequest teamRequest){
        if(id == null || teamRequest == null){
            throw new BaseException(ErrorCode.NO_DATA);
        }

        Team team = teamRepo.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_DATA));

        team.update(teamRequest);
        return TeamResponse.fromResponse(team);
    }

    @Transactional
    public boolean removeTeam(Long id){
        if(id == null){
           return false;
        }
        teamRepo.deleteById(id);
        return true;
    }





}
