package com.travel.leave.schedule.service;

import com.travel.leave.ai_travel.entity.TravelLocation;
import com.travel.leave.ai_travel.repository.TravelLocationRepository;
import com.travel.leave.schedule.dto.get.TravelLocationRequestDTOs;
import com.travel.leave.schedule.service.model.mapper.TravelLocationFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelLocationService {
    private final TravelLocationRepository travelLocationRepository;
    private final TravelLocationFactory travelLocationFactory;

    public List<TravelLocation> save(Long travelCode, List<TravelLocationRequestDTOs> schedule) {
        return travelLocationRepository.saveAll(travelLocationFactory.generate(travelCode, schedule));
    }
}
