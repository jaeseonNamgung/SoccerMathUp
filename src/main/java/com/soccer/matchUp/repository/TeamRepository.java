package com.soccer.matchUp.repository;

import com.soccer.matchUp.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("select distinct t from Team t join fetch t.teamMembers")
    Optional<Team> findById(Long id);
}
