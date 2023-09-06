package com.soccer.matchUp.service;

import com.soccer.matchUp.dto.request.ScorerRequest;
import com.soccer.matchUp.dto.response.ScorerResponse;
import com.soccer.matchUp.entity.Scorer;
import com.soccer.matchUp.error.BaseException;
import com.soccer.matchUp.error.ErrorCode;
import com.soccer.matchUp.repository.ScorerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ScorerServiceTest {

    @InjectMocks
    private ScorerService scorerService;

    @Mock
    private ScorerRepository scorerRepo;
    
    @Test
    public void addScorerSuccessTest()throws Exception{
        // given
        Scorer scorer = createScorer("홍길동", 10, 2);
        given(scorerRepo.save(any())).willReturn(scorer);
        // when
        ScorerRequest scorerRequest = createScorerRequest(1L,"홍길동", 10, 2);
        scorerService.addScorer(scorerRequest);
        // then
        then(scorerRepo).should().save(any());
    }
    @Test
    public void addScorerWithNullScorerRequestTest()throws Exception{
        // given
        
        // when
        Exception e = catchException(() -> scorerService.addScorer(null));
        // then
        assertThat(e)
                .isInstanceOf(BaseException.class)
                .hasMessageContaining(ErrorCode.NO_DATA.getMessage());
    }

    @Test
    public void getScorersSuccessTest()throws Exception{
        // given
        Scorer scorer1 = createScorer("홍길동", 10, 2);
        Scorer scorer2 = createScorer("김길동", 7, 1);
        given(scorerRepo.findAllByOrderByNameAsc()).willReturn(
                Arrays.asList(scorer2, scorer1)
        );
        // when
        List<ScorerResponse> result = scorerService.getScorers();
        // then
        assertThat(result)
                .hasSize(2)
                .extracting("name").containsExactly(scorer2.getName(), scorer1.getName());
        then(scorerRepo).should().findAllByOrderByNameAsc();
    }

    @Test
    public void modifyScorerSuccessTest()throws Exception{
        // given
        Scorer scorer = createScorer("홍길동", 10, 2);
        given(scorerRepo.findById(anyLong()))
                .willReturn(Optional.of(scorer));
        // when
        ScorerRequest modifyScorerRequest = createScorerRequest(1L, "김길동", 7, 2);
        scorerService.modifyScorer(1L, modifyScorerRequest);
        // then
        assertThat(scorer).extracting("name", "number").containsExactly("김길동", 7);
        then(scorerRepo).should().findById(anyLong());
    }

    @Test
    public void modifyScorerWithNullIdAndNullScorerRequestTest()throws Exception{
        // given
        ScorerRequest modifyScorerRequest = createScorerRequest(1L, "김길동", 7, 2);
        // when
        Exception e = catchException(() -> scorerService.modifyScorer(null, modifyScorerRequest));
        // then
        assertThat(e)
                .isInstanceOf(BaseException.class)
                .hasMessageContaining(ErrorCode.NO_DATA.getMessage());
    }
    @Test
    public void modifyScorerWithNullIdTest()throws Exception{
        // given
        // when
        Exception e = catchException(() -> scorerService.modifyScorer(null, null));
        // then
        assertThat(e)
                .isInstanceOf(BaseException.class)
                .hasMessageContaining(ErrorCode.NO_DATA.getMessage());
    }
    @Test
    public void modifyScorerWithNullScorerRequestTest()throws Exception{
        // given
        // when
        Exception e = catchException(() -> scorerService.modifyScorer(1L, null));
        // then
        assertThat(e)
                .isInstanceOf(BaseException.class)
                .hasMessageContaining(ErrorCode.NO_DATA.getMessage());
    }


    @Test
    public void removeScorerSuccessTest()throws Exception{
        // given
        Long id = 1L;
        willDoNothing().given(scorerRepo).deleteById(id);
        // when
        scorerService.removeScorer(id);
        // then
        assertThat(scorerRepo.findById(id)).isEmpty();
        then(scorerRepo).should().deleteById(id);
    }

    @Test
    public void removeScorerWithNullIdTest()throws Exception{
        // given

        // when
        Exception e = catchException(() -> scorerService.removeScorer(null));
        // then
        assertThat(e)
                .isInstanceOf(BaseException.class)
                .hasMessageContaining(ErrorCode.NO_DATA.getMessage());
    }




    private ScorerRequest createScorerRequest(Long id, String name, int number, int goalCount) {
        return ScorerRequest.of(
                id,
                name
                ,number,
                goalCount
        );
    }

    private Scorer createScorer(String name, int number, int goalCount) {
        return Scorer.builder()
                .name(name)
                .number(number)
                .goalCount(goalCount)
                .build();
    }

}