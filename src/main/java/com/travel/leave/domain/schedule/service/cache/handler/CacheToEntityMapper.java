package com.travel.leave.domain.schedule.service.cache.handler;

import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.preparation.PreparationMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline.GeoGraphicMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline.ScheduleMessage;
import com.travel.leave.domain.schedule.service.cache.TravelCache;

import com.travel.leave.subdomain.travel.entity.Travel;
import com.travel.leave.subdomain.travellocaion.entity.TravelLocation;
import com.travel.leave.subdomain.travelpreparation.entity.TravelPreparation;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CacheToEntityMapper {
    public Travel mapToTravel(TravelCache travelCache) {
        return Travel.builder()
                .code(travelCache.getTravelCode())
                .name(travelCache.getTitle())
                .content(travelCache.getInformation())
                .startDate(Date.valueOf(travelCache.getStartDate()))
                .endDate(Date.valueOf(travelCache.getEndDate()))
                .imageUrl(travelCache.getImageUrl().get(0)).build();
    }

    public List<TravelLocation> mapToTravelLocation(List<ScheduleMessage> schedule, List<GeoGraphicMessage> geographicMessages) {
        return getTravelLocations(schedule, geographicMessages);
    }

    public List<TravelPreparation> mapToTravelPreparation(Long travelCode, List<PreparationMessage> preparation) {
        return getTravelPreparations(travelCode, preparation);
    }

    private List<TravelPreparation> getTravelPreparations(Long travelCode, List<PreparationMessage> preparations) {
        List<TravelPreparation> travelPreparations = new ArrayList<>();
        for(PreparationMessage preparation : preparations){
            travelPreparations.add(TravelPreparation.of(preparation.code(), preparation.itemName(), preparation.quantity(), preparation.isDeleted(), travelCode));
        }
        return travelPreparations;
    }

    private List<TravelLocation> getTravelLocations(List<ScheduleMessage> schedules, List<GeoGraphicMessage> geographicMessages) {
        List<TravelLocation> travelLocations = new ArrayList<>();
        return travelLocations;
    }
}
