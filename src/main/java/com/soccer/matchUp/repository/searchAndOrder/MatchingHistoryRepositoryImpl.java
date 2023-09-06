package com.soccer.matchUp.repository.searchAndOrder;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soccer.matchUp.entity.MatchingHistory;
import com.soccer.matchUp.repository.searchAndOrder.searchCondition.MatchingHistorySearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static com.soccer.matchUp.entity.QMatchingHistory.matchingHistory;
import static com.soccer.matchUp.entity.QScorer.scorer;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class MatchingHistoryRepositoryImpl implements MatchingHistoryRepositoryCustom {


    private final JPAQueryFactory queryFactory;

    @Override
    public List<MatchingHistory> findSearchAndSortAll(MatchingHistorySearchCondition mhSearchCond, Pageable pageable) {

        List<OrderSpecifier> orders = getOrderSpecifier(pageable);
        return queryFactory
                .selectFrom(matchingHistory)
                .leftJoin(matchingHistory.scorers, scorer)
                .fetchJoin()
                .where(
                        homeTeamContains(mhSearchCond.homeTeamName()),
                        awayTeamContains(mhSearchCond.awayTeamName()),
                        scoreContains(mhSearchCond.scorer()),
                        matchHistoryAgainstContains(mhSearchCond.matchHistoryAgainst()),
                        detail(mhSearchCond.detail())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders.stream().toArray(OrderSpecifier[]::new))
                .fetch();
    }


    private BooleanExpression homeTeamContains(String homeTeamName) {
        return hasText(homeTeamName) ? matchingHistory.homeTeamName.contains(homeTeamName) : null;
    }

    private BooleanExpression awayTeamContains(String awayTeamName) {
        return hasText(awayTeamName) ? matchingHistory.awayTeamName.contains(awayTeamName) : null;
    }

    private BooleanExpression scoreContains(String score) {
        return score != null ? matchingHistory.score.contains(score) : null;
    }

    private BooleanExpression matchHistoryAgainstContains(String matchHistoryAgainst) {
        return hasText(matchHistoryAgainst) ? matchingHistory.matchHistoryAgainst.contains(matchHistoryAgainst):null;
    }

    private Predicate detail(String detail) {
        return hasText(detail) ? matchingHistory.detail.contains(detail):null;
    }

    private List<OrderSpecifier> getOrderSpecifier(Pageable pageable) {

        List<OrderSpecifier> orders = new ArrayList<>();

        if (pageable.getSort().isUnsorted()) {
            return null;
        }

        for (Sort.Order order : pageable.getSort()) {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

            switch (order.getProperty()) {
                case "createdDate":
                    OrderSpecifier<?> orderDate = QueryDslUtil.getSortedColumn(direction, matchingHistory, "createdDate");
                    orders.add(orderDate);
                    break;
                case "matchDate":
                    OrderSpecifier<?> orderMatchDate = QueryDslUtil.getSortedColumn(direction, matchingHistory, "matchDate");
                    orders.add(orderMatchDate);
                    break;
                default:
                    break;

            }
        }
        return orders;
    }

}

