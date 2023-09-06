package com.soccer.matchUp.repository;

import com.soccer.matchUp.entity.FieldReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldReservationRepository extends JpaRepository<FieldReservation, Long> {

    /*
    * 1. 구장 예약 저장
    * 2. 구장 예약 단건 조회
    * 3. 구장 예약 전체 조회(페이징)
    * */

}
