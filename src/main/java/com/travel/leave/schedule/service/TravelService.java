package com.travel.leave.schedule.service;

import com.travel.leave.ai_travel.entity.Travel;
import com.travel.leave.ai_travel.repository.TravelRepository;
import com.travel.leave.schedule.dto.get.TravelRequestDTO;
import com.travel.leave.schedule.service.model.mapper.TravelFactory;
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
