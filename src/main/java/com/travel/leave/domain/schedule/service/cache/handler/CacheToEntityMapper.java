package com.travel.leave.domain.schedule.service.cache.handler;

import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.preparation.PreparationMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline.GeoGraphicMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline.ScheduleMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline.TimeLineMessage;
import com.travel.leave.domain.schedule.service.cache.TravelCache;

import com.travel.leave.subdomain.travel.entity.Travel;
import com.travel.leave.subdomain.travellocaion.entity.GeographicDetails;
import com.travel.leave.subdomain.travellocaion.entity.ScheduleDetails;
import com.travel.leave.subdomain.travellocaion.entity.TravelLocation;
import com.travel.leave.subdomain.travelpreparation.entity.TravelPreparation;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public List<TravelLocation> mapToTravelLocation(List<ScheduleMessage> schedule, List<GeoGraphicMessage> geographicMessages, Long travelCode) {
        return getTravelLocations(schedule, geographicMessages, travelCode);
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

    private List<TravelLocation> getTravelLocations(List<ScheduleMessage> schedules, List<GeoGraphicMessage> geographicMessages, Long travelCode) {
        List<TravelLocation> travelLocations = new ArrayList<>();
        for(ScheduleMessage schedule : schedules){
            for(int i = 0; i < schedule.timeLines().size(); i++){
                TimeLineMessage timeLineMessage = schedule.timeLines().get(i);
                TimeLineMessage nextTimeLineMessage = schedule.timeLines().get(i);
                if(i != schedule.timeLines().size()-1){
                    nextTimeLineMessage = schedule.timeLines().get(i+1);
                }
                GeoGraphicMessage geoGraphicMessage = getGeographicMessages(geographicMessages, timeLineMessage.id());

                TravelLocation.builder()
                        .travelLocationCode(timeLineMessage.id())
                        .scheduleDetails(ScheduleDetails.builder()
                                .name(timeLineMessage.title())
                                .content(timeLineMessage.content())
                                .step(i)
                                .startTime(timeLineMessage.time())
                                .endTime(nextTimeLineMessage.time())
                                .geographicDetails(GeographicDetails.builder()
                                        .latitude(geoGraphicMessage.latitude())
                                        .longitude(geoGraphicMessage.longitude())
                                        .build()
                                )
                                .build()
                        )
                        .travelCode(travelCode)
                        .build();
            }
        }
        return travelLocations;
    }

    private GeoGraphicMessage getGeographicMessages(List<GeoGraphicMessage> geographicMessages, Long id) {
        return geographicMessages.stream()
                .filter(geoGraphicMessage -> Objects.equals(geoGraphicMessage.travelLocationCode(), id))
                .findFirst().get();
    }
}
