package com.soccer.matchUp.repository;

import com.soccer.matchUp.config.JpaQueryFactoryConfig;
import com.soccer.matchUp.entity.MatchingHistory;
import com.soccer.matchUp.entity.Scorer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(JpaQueryFactoryConfig.class)
@DataJpaTest
class ScorerRepositoryTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ScorerRepository scorerRepo;

    @Test
    public void findAllByOrderByNameAscSuccessTest()throws Exception{
        // given
        MatchingHistory matchingHistory = createMatchingHistory();
        entityManager.persist(matchingHistory);
        Scorer scorer1 = createScorer("홍길동",10, 2,matchingHistory);
        Scorer scorer2 = createScorer("김길동",7, 1, matchingHistory);
        entityManager.persist(scorer1);
        entityManager.persist(scorer2);
        // when
        List<Scorer> result = scorerRepo.findAllByOrderByNameAsc();
        // then
        assertThat(result)
                .hasSize(2);
        assertThat(result)
                .extracting("name").containsExactly("김길동", "홍길동");
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

    private Scorer createScorer(String name, int number, int goalCount, MatchingHistory matchingHistory) {
        return Scorer.builder()
                .name(name)
                .number(number)
                .goalCount(goalCount)
                .matchingHistory(matchingHistory)
                .build();
    }

}