package com.travel.leave.subdomain.travel.service;

import com.travel.leave.subdomain.travel.entity.Travel;
import com.travel.leave.subdomain.travel.repository.TravelRepository;
import com.travel.leave.domain.schedule.dto.get.TravelRequestDTO;
import com.travel.leave.domain.schedule.service.mapper.TravelFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelService {
    private final TravelRepository travelRepository;
    private final TravelFactory travelFactory;

    public Travel save(TravelRequestDTO travelRequestDTO) {
        return travelRepository.save(travelFactory.generate(travelRequestDTO));
    }
}
