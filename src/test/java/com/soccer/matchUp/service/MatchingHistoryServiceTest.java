package com.soccer.matchUp.service;


import com.soccer.matchUp.dto.request.MatchingHistoryRequest;
import com.soccer.matchUp.dto.response.MatchingHistoryResponse;
import com.soccer.matchUp.entity.MatchingHistory;
import com.soccer.matchUp.error.BaseException;
import com.soccer.matchUp.error.ErrorCode;
import com.soccer.matchUp.repository.MatchingHistoryRepository;
import com.soccer.matchUp.repository.ScorerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class MatchingHistoryServiceTest {

    @InjectMocks
    private MatchingHistoryService mHistoryService;

    @Mock
    private MatchingHistoryRepository mHistoryRepo;
    @Mock
    private ScorerRepository scorerRepo;

    @Test
    public void addMatchingHistorySuccessTest()throws Exception{
        // given
        MatchingHistory matchingHistory = createMatchingHistory();

        given(mHistoryRepo.save(any())).willReturn(matchingHistory);
        // when
        MatchingHistoryRequest matchingHistoryRequest = createMatchingHistoryRequest();
        mHistoryService.addMatchingHistory(matchingHistoryRequest);
        // then
        then(mHistoryRepo).should().save(any());
    }

    @Test
    public void getMatchingHistorySuccessTest()throws Exception{
        // given
        MatchingHistory matchingHistory = createMatchingHistory();
        given(mHistoryRepo.findById(anyLong())).willReturn(Optional.of(matchingHistory));
        // when
        MatchingHistoryRequest mHistoryRequest = createMatchingHistoryRequest();
        MatchingHistoryResponse result = mHistoryService.getMatchingHistory(mHistoryRequest.id());

        // then
        assertThat(result)
                .hasFieldOrPropertyWithValue("homeTeamName", "home team")
                .hasFieldOrPropertyWithValue("awayTeamName", "away team")
                .hasFieldOrPropertyWithValue("score", "2:0")
                .hasFieldOrPropertyWithValue("matchHistoryAgainst", "1승3무2패")
                .hasFieldOrPropertyWithValue("detail", "detail")
                .hasFieldOrPropertyWithValue("matchDate", LocalDateTime.of(2023,06,29,14,48));
        then(mHistoryRepo).should().findById(anyLong());
    }

    @Test
    public void getMatchingHistoryNotFoundDataTest()throws Exception{
        // given
        BaseException exception = new BaseException(ErrorCode.NOT_FOUND_DATA);
        willThrow(exception).given(mHistoryRepo).findById(anyLong());
        // when
        Exception result = catchException(() -> mHistoryService.getMatchingHistory(2L));

        // then
        assertThat(result).hasMessageContaining(ErrorCode.NOT_FOUND_DATA.getMessage())
                .isInstanceOf(BaseException.class);
        then(mHistoryRepo).should().findById(anyLong());
    }

    @Test
    public void modifyMatchingHistorySuccessTest()throws Exception{
        // given
        MatchingHistoryRequest updateMHistoryRequest = modifyMatchingHistoryRequest();
        MatchingHistory matchingHistory = createMatchingHistory();
        given(mHistoryRepo.findById(anyLong())).willReturn(Optional.of(matchingHistory));
        // when
        mHistoryService.modifyMatchingHistory(1L, updateMHistoryRequest);
        // then
        assertThat(matchingHistory)
                .hasFieldOrPropertyWithValue("score", updateMHistoryRequest.score())
                .hasFieldOrPropertyWithValue("matchHistoryAgainst", updateMHistoryRequest.matchHistoryAgainst())
                .hasFieldOrPropertyWithValue("detail", updateMHistoryRequest.detail());

        then(mHistoryRepo).should().findById(anyLong());

    }

    @Test
    public void modifyMatchingHistoryNotFoundDataTest()throws Exception{
        // given
        MatchingHistoryRequest updateMHistoryRequest = modifyMatchingHistoryRequest();
        BaseException baseException = new BaseException(ErrorCode.NOT_FOUND_DATA);
        willThrow(baseException).given(mHistoryRepo).findById(anyLong());
        // when
        Exception result = catchException(() -> mHistoryService.modifyMatchingHistory(2L, updateMHistoryRequest));
        // then
        assertThat(result).isInstanceOf(BaseException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND_DATA.getMessage());
        then(mHistoryRepo).should().findById(anyLong());
    }
    @Test
    public void modifyMatchingHistoryWithNullIdTest()throws Exception{
        // given
        MatchingHistoryRequest updateMHistoryRequest = modifyMatchingHistoryRequest();
        // when
        Exception e = catchException(() -> mHistoryService.modifyMatchingHistory(null, updateMHistoryRequest));
        // then
        assertThat(e)
                .isInstanceOf(BaseException.class)
                .hasMessageContaining(ErrorCode.NO_DATA.getMessage());
    }
    @Test
    public void modifyMatchingHistoryWithNullMHistoryRequestTest()throws Exception{
        // given
        // when
        Exception e = catchException(() -> mHistoryService.modifyMatchingHistory(1L, null));
        // then
        assertThat(e)
                .isInstanceOf(BaseException.class)
                .hasMessageContaining(ErrorCode.NO_DATA.getMessage());
    }

    @Test
    public void modifyMatchingHistoryWithNullIdAndNullMHistoryRequestTest()throws Exception{
        // given
        // when
        Exception e = catchException(() -> mHistoryService.modifyMatchingHistory(null, null));
        // then
        assertThat(e)
                .isInstanceOf(BaseException.class)
                .hasMessageContaining(ErrorCode.NO_DATA.getMessage());
    }
    
    @Test
    public void removeMatchingHistorySuccessTest()throws Exception{
        // given
        willDoNothing().given(mHistoryRepo).deleteById(1L);
        // when
        boolean result = mHistoryService.removeMatchingHistory(1L);
        // then
        assertThat(result).isTrue();
        then(mHistoryRepo).should().deleteById(1L);
    }
    
    @Test
    public void removeMatchingHistoryWithNullIdTest()throws Exception{
        // given
        
        // when
        boolean result = mHistoryService.removeMatchingHistory(null);
        // then
        assertThat(result).isFalse();
    }


    private static MatchingHistoryRequest modifyMatchingHistoryRequest() {
       return MatchingHistoryRequest.of(
                1L,
                "home team",
                "away team",
                "5:3",
                "3승4무1패",
                "no detail",
                LocalDateTime.of(2023,06,29,14,48)
        );
    }

    private static MatchingHistoryRequest createMatchingHistoryRequest() {
        return MatchingHistoryRequest.of(
                1L,
                "home team",
                "away team",
                "2:0",
                "1승3무2패",
                "detail",
                LocalDateTime.of(2023,06,29,14,48)
        );
    }


    private static MatchingHistory createMatchingHistory() {
        return MatchingHistory.builder()
                .homeTeamName("home team")
                .awayTeamName("away team")
                .score("2:0")
                .matchHistoryAgainst("1승3무2패")
                .detail("detail")
                .matchDate(LocalDateTime.of(2023,06,29,14,48))
                .build();

    }

}