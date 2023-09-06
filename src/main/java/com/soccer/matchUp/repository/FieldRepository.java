package com.soccer.matchUp.repository;

import com.soccer.matchUp.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FieldRepository extends JpaRepository<Field, Long> {

    /*
    * 1. 전체 구장 조회 (페이지 적용)
    * 2. 페이지 상세 조회(단건 조회)
    * 3. 축구장 저장
    * */
    Field save(Field field);
    Optional<Field> findBySvcId(Long svcId);
}
