package com.soccer.matchUp.repository;

import com.soccer.matchUp.config.JpaQueryFactoryConfig;
import com.soccer.matchUp.entity.MatchingHistory;
import com.soccer.matchUp.entity.Scorer;
import com.soccer.matchUp.repository.searchAndOrder.searchCondition.MatchingHistorySearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(JpaQueryFactoryConfig.class)
@DataJpaTest
class MatchingHistoryRepositoryTest {

    @Autowired
    private MatchingHistoryRepository mHistoryRepo;

    @Test
    public void findByIdSuccessTest()throws Exception{
        // given
        MatchingHistory matchingHistory = createMatchingHistory();
        createScorer("홍길동", 10, 2, matchingHistory);
        createScorer("김길동", 7, 1, matchingHistory);

        mHistoryRepo.save(matchingHistory);
        // when
        MatchingHistory result = mHistoryRepo.findById(1L).get();

        // then
        assertThat(result)
                .hasFieldOrPropertyWithValue("homeTeamName", "home team")
                .hasFieldOrPropertyWithValue("awayTeamName", "away team")
                .hasFieldOrPropertyWithValue("score","2:0")
                .hasFieldOrPropertyWithValue("matchHistoryAgainst", "1승3무2패")
                .hasFieldOrPropertyWithValue("detail", "detail");
        assertThat(result.getScorers().get(0))
                .hasFieldOrPropertyWithValue("name", "홍길동")
                .hasFieldOrPropertyWithValue("number", 10)
                .hasFieldOrPropertyWithValue("goalCount", 2);
        assertThat(result.getScorers()).size().isEqualTo(2);
    }

    @Test
    public void findByNameSearchAllSuccessTest()throws Exception{
        // given
        MatchingHistory matchingHistory1 = createMatchingHistory("home team");
        MatchingHistory matchingHistory2 = createMatchingHistory("home team");
        MatchingHistory matchingHistory3 = createMatchingHistory("away");

        mHistoryRepo.save(matchingHistory1);
        mHistoryRepo.save(matchingHistory2);
        mHistoryRepo.save(matchingHistory3);
        // when
        MatchingHistorySearchCondition searchCondition =
                MatchingHistorySearchCondition.of("home", null, null, null, null);
        PageRequest pageRequest = PageRequest.of(0,5,Sort.by("createdDate").descending());
        List<MatchingHistory> searchList = mHistoryRepo.findSearchAndSortAll(searchCondition, pageRequest);
        // then
        assertThat(searchList).size().isEqualTo(2);
        assertThat(searchList.get(0)).hasFieldOrPropertyWithValue("homeTeamName", "home team")
                .hasFieldOrPropertyWithValue("awayTeamName", "away team")
                .hasFieldOrPropertyWithValue("score","2:0")
                .hasFieldOrPropertyWithValue("matchHistoryAgainst", "1승3무2패")
                .hasFieldOrPropertyWithValue("detail", "detail");
    }
    @Test
    public void notFindByNameSearchAllTest()throws Exception{
        // given
        MatchingHistory matchingHistory1 = createMatchingHistory("home team");
        MatchingHistory matchingHistory2 = createMatchingHistory("home team");
        MatchingHistory matchingHistory3 = createMatchingHistory("away");

        mHistoryRepo.save(matchingHistory1);
        mHistoryRepo.save(matchingHistory2);
        mHistoryRepo.save(matchingHistory3);
        // when
        MatchingHistorySearchCondition searchCondition =
                MatchingHistorySearchCondition.of(null,null, null, null, null);
        PageRequest pageRequest = PageRequest.of(0,5,Sort.by("createdDate").descending());
        List<MatchingHistory> searchList = mHistoryRepo.findSearchAndSortAll(searchCondition, pageRequest);
        // then
        assertThat(searchList).size().isEqualTo(3);
    }

    @Test
    public void findByAllCreatedDateOrderDescSuccessTest()throws Exception{
        // given
        MatchingHistory matchingHistory1 = createMatchingHistory("home team");
        MatchingHistory matchingHistory2 = createMatchingHistory("home team");
        MatchingHistory matchingHistory3 = createMatchingHistory("away");

        mHistoryRepo.save(matchingHistory1);
        mHistoryRepo.save(matchingHistory2);
        mHistoryRepo.save(matchingHistory3);
        // when
        MatchingHistorySearchCondition searchCondition =
                MatchingHistorySearchCondition.of(null,null, null, null, null);
        PageRequest pageRequest = PageRequest.of(0,5,Sort.by("createdDate").descending());
        List<MatchingHistory> searchList = mHistoryRepo.findSearchAndSortAll(searchCondition, pageRequest);
        // then
        assertThat(searchList).size().isEqualTo(3);
        assertThat(searchList.get(0)).hasFieldOrPropertyWithValue("homeTeamName", "away");

    }
    @Test
    public void findByAllCreatedDateOrderAscSuccessTest()throws Exception{
        // given
        MatchingHistory matchingHistory1 = createMatchingHistory("home team");
        MatchingHistory matchingHistory2 = createMatchingHistory("home t");
        MatchingHistory matchingHistory3 = createMatchingHistory("away");

        mHistoryRepo.save(matchingHistory1);
        mHistoryRepo.save(matchingHistory2);
        mHistoryRepo.save(matchingHistory3);
        // when
        MatchingHistorySearchCondition searchCondition =
                MatchingHistorySearchCondition.of(null,null, null, null, null);
        PageRequest pageRequest = PageRequest.of(0,5,Sort.by("createdDate").ascending());
        List<MatchingHistory> searchList = mHistoryRepo.findSearchAndSortAll(searchCondition, pageRequest);
        // then
        assertThat(searchList).size().isEqualTo(3);
        assertThat(searchList.get(0)).hasFieldOrPropertyWithValue("homeTeamName", "home team");

    }

    @Test
    public void findByAllMathDateOrderDescSuccessTest()throws Exception{
        // given
        MatchingHistory matchingHistory1 = createMatchingHistory("home team", LocalDateTime.of(2023, 06, 29, 18, 17));
        MatchingHistory matchingHistory2 = createMatchingHistory("home t", LocalDateTime.of(2010, 06, 29, 18, 17));
        MatchingHistory matchingHistory3 = createMatchingHistory("away", LocalDateTime.of(2015, 06, 29, 18, 17));

        mHistoryRepo.save(matchingHistory1);
        mHistoryRepo.save(matchingHistory2);
        mHistoryRepo.save(matchingHistory3);
        // when
        MatchingHistorySearchCondition searchCondition =
                MatchingHistorySearchCondition.of(null,null, null, null, null);
        PageRequest pageRequest = PageRequest.of(0,5,Sort.by("matchDate").descending());
        List<MatchingHistory> searchList = mHistoryRepo.findSearchAndSortAll(searchCondition, pageRequest);
        // then
        assertThat(searchList).size().isEqualTo(3);
        assertThat(searchList.get(0)).hasFieldOrPropertyWithValue("homeTeamName", "home team");

    }
    @Test
    public void findByAllMathDateOrderAscSuccessTest()throws Exception{
        // given
        MatchingHistory matchingHistory1 = createMatchingHistory("home team", LocalDateTime.of(2023, 06, 29, 18, 17));
        MatchingHistory matchingHistory2 = createMatchingHistory("home t", LocalDateTime.of(2010, 06, 29, 18, 17));
        MatchingHistory matchingHistory3 = createMatchingHistory("away", LocalDateTime.of(2015, 06, 29, 18, 17));

        mHistoryRepo.save(matchingHistory1);
        mHistoryRepo.save(matchingHistory2);
        mHistoryRepo.save(matchingHistory3);
        // when
        MatchingHistorySearchCondition searchCondition =
                MatchingHistorySearchCondition.of(null,null, null, null, null);
        PageRequest pageRequest = PageRequest.of(0,5,Sort.by("matchDate").ascending());
        List<MatchingHistory> searchList = mHistoryRepo.findSearchAndSortAll(searchCondition, pageRequest);
        // then
        assertThat(searchList).size().isEqualTo(3);
        assertThat(searchList.get(0)).hasFieldOrPropertyWithValue("homeTeamName", "home t");

    }


    private static Scorer createScorer(String name, Integer number, Integer goalCount, MatchingHistory mathingHistory) {
        Scorer scorer = Scorer.builder()
                .name(name)
                .number(number)
                .goalCount(goalCount)
                .build();
        scorer.setMatchingHistory(mathingHistory);
        return scorer;
    }
    private static MatchingHistory createMatchingHistory() {
        return MatchingHistory.builder()
                .homeTeamName("home team")
                .awayTeamName("away team")
                .score("2:0")
                .matchHistoryAgainst("1승3무2패")
                .detail("detail")
                .build();
    }
    private static MatchingHistory createMatchingHistory(String homeTeamName) {
        return MatchingHistory.builder()
                .homeTeamName(homeTeamName)
                .awayTeamName("away team")
                .score("2:0")
                .matchHistoryAgainst("1승3무2패")
                .detail("detail")
                .build();
    }private static MatchingHistory createMatchingHistory(String homeTeamName, LocalDateTime matchDate) {
        return MatchingHistory.builder()
                .homeTeamName(homeTeamName)
                .awayTeamName("away team")
                .score("2:0")
                .matchHistoryAgainst("1승3무2패")
                .detail("detail")
                .matchDate(matchDate)
                .build();
    }

}