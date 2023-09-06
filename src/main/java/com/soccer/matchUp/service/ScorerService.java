package com.soccer.matchUp.service;

import com.soccer.matchUp.dto.response.ScorerResponse;
import com.soccer.matchUp.dto.request.ScorerRequest;
import com.soccer.matchUp.entity.Scorer;
import com.soccer.matchUp.error.BaseException;
import com.soccer.matchUp.error.ErrorCode;
import com.soccer.matchUp.repository.ScorerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ScorerService {

    private final ScorerRepository scorerRepo;

    @Transactional
    public void addScorer(ScorerRequest scorerRequest){

        if(scorerRequest == null){
           throw new BaseException(ErrorCode.NO_DATA);
        }

        scorerRepo.save(scorerRequest.toScorer());
    }

    public List<ScorerResponse> getScorers(){
        return scorerRepo.findAllByOrderByNameAsc().stream().map(ScorerResponse::from).collect(Collectors.toList());
    }

    @Transactional
    public void modifyScorer(Long id, ScorerRequest scorerRequest){
        if(id == null || scorerRequest == null){
            throw new BaseException(ErrorCode.NO_DATA);
        }
        Scorer scorer = scorerRepo
                .findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_DATA));
        scorer.update(scorerRequest);
    }

    @Transactional
    public void removeScorer(Long id){
        if(id == null){
            throw new BaseException(ErrorCode.NO_DATA);
        }
        scorerRepo.deleteById(id);
    }
}
