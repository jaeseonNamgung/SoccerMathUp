package com.soccer.matchUp.service;

import com.soccer.matchUp.dto.request.TeamMemberRequest;
import com.soccer.matchUp.dto.response.TeamMemberResponse;
import com.soccer.matchUp.entity.Team;
import com.soccer.matchUp.entity.TeamMember;
import com.soccer.matchUp.error.BaseException;
import com.soccer.matchUp.error.ErrorCode;
import com.soccer.matchUp.repository.TeamMemberRepository;
import com.soccer.matchUp.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepo;
    private final TeamRepository teamRepository;

    @Transactional
    public boolean addTeamMember(Long teamId, TeamMemberRequest teamMemberRequest){

        if(teamId == null || teamMemberRequest == null){
            throw new BaseException(ErrorCode.NO_DATA);
        }
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_DATA));

        TeamMember teamMember = teamMemberRequest.toEntity(team);
        teamMemberRepo.save(teamMember);
        return true;
    }

    public List<TeamMemberResponse> getTeamMembers(){
        return teamMemberRepo
                .findAllByOrderByNameAsc()
                .stream()
                .map(TeamMemberResponse::fromResponse)
                .collect(Collectors.toList());
    }

    public void modifyTeamMember(Long id, TeamMemberRequest teamMemberRequest){
        if(id == null || teamMemberRequest == null){
            throw new BaseException(ErrorCode.NO_DATA);
        }

        TeamMember teamMember = teamMemberRepo
                .findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_DATA));
            teamMember.update(teamMemberRequest);
    }

    public void removeTeamMember(Long id){
        if(id == null){
            throw new BaseException(ErrorCode.NO_DATA);
        }
        teamMemberRepo.deleteById(id);
    }

}

