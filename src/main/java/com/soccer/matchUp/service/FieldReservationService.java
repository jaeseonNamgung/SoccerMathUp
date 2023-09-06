package com.soccer.matchUp.service;

import com.soccer.matchUp.dto.request.FieldRequest;
import com.soccer.matchUp.dto.request.FieldReservationRequest;
import com.soccer.matchUp.repository.FieldReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FieldReservationService {

    private final FieldReservationRepository frRepository;
    public List<FieldReservationRequest> getFieldReservation(FieldRequest fieldDto) {
        return null;
    }
}
