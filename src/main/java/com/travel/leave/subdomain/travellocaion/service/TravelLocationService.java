package com.travel.leave.subdomain.travellocaion.service;

import com.travel.leave.subdomain.travellocaion.entity.TravelLocation;
import com.travel.leave.subdomain.travellocaion.repository.TravelLocationRepository;
import com.travel.leave.domain.schedule.dto.get.TravelLocationRequestDTOs;
import com.travel.leave.domain.schedule.service.mapper.TravelLocationFactory;
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
