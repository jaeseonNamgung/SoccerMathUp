package com.soccer.matchUp.repository;

import com.soccer.matchUp.entity.Scorer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScorerRepository extends JpaRepository<Scorer, Long> {

    List<Scorer> findAllByOrderByNameAsc();

}
