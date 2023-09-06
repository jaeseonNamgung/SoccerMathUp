package com.soccer.matchUp.service;

import com.soccer.matchUp.dto.request.MatchingHistoryRequest;
import com.soccer.matchUp.dto.response.MatchingHistoryResponse;
import com.soccer.matchUp.entity.MatchingHistory;
import com.soccer.matchUp.error.BaseException;
import com.soccer.matchUp.error.ErrorCode;
import com.soccer.matchUp.repository.MatchingHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MatchingHistoryService {

    private final MatchingHistoryRepository mHistoryRepo;

    @Transactional
    public void addMatchingHistory(MatchingHistoryRequest mHistoryDto) {
        mHistoryRepo.save(mHistoryDto.toMathingHistory());
    }

    public MatchingHistoryResponse getMatchingHistory(Long id){
        return MatchingHistoryResponse.fromResponse(
                mHistoryRepo.findById(id)
                        .orElseThrow(()->new BaseException(ErrorCode.NOT_FOUND_DATA))
        );
    }

    @Transactional
    public void modifyMatchingHistory(Long id, MatchingHistoryRequest mHistoryRequest){

        if(id == null || mHistoryRequest == null){
            throw new BaseException(ErrorCode.NO_DATA);
        }
        MatchingHistory matchingHistory = mHistoryRepo.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_DATA));
        matchingHistory.update(mHistoryRequest);
    }

    @Transactional
    public boolean removeMatchingHistory(Long id){
            if(id == null){
                return false;
            }
            mHistoryRepo.deleteById(id);
            return true;

    }



}
