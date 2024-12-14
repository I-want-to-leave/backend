package com.travel.leave.schedule.service.model.cache.factory;

import com.travel.leave.schedule.controller.socket.messageFormat.preparation.PreparationMessage;
import com.travel.leave.schedule.controller.socket.messageFormat.preparation.UpdateTravelPreparationMessage;
import com.travel.leave.schedule.controller.socket.messageFormat.timeline.ScheduleMessage;
import com.travel.leave.schedule.controller.socket.messageFormat.timeline.TimeLineMessage;
import com.travel.leave.schedule.controller.socket.messageFormat.timeline.UpdateTravelLocationMessage;
import com.travel.leave.schedule.controller.socket.messageFormat.travel.UpdateTravelContentMessage;
import com.travel.leave.schedule.controller.socket.messageFormat.travel.UpdateTravelNameMessage;
import com.travel.leave.schedule.service.model.cache.TravelCache;
import com.travel.leave.travel.entity.Travel;
import com.travel.leave.travel.entity.TravelLocation;
import com.travel.leave.travel.entity.TravelPreparation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TravelCacheFactory {

    //소켓 message를 캐시로 바꾸는 로직-============================================================================
    public TravelCache createUpdatedTravelContent(TravelCache travelCache,
                                                  UpdateTravelContentMessage updateTravelContentMessage) {
        travelCache.setInformation(updateTravelContentMessage.travelContent());
        return travelCache;
    }

    public TravelCache createUpdatedTravelPreparation(TravelCache travelCache,
                                                      UpdateTravelPreparationMessage updateTravelPreparationMessage) {
        travelCache.setPreparation(updateTravelPreparationMessage.preparations());
        return travelCache;
    }

    public TravelCache createUpdatedTravelLocation(TravelCache travelCache,
                                                   UpdateTravelLocationMessage updateTravelLocationMessage) {
        travelCache.setSchedule(updateTravelLocationMessage.schedule());
        return travelCache;
    }

    public TravelCache createUpdatedTravelName(TravelCache travelCache,
                                               UpdateTravelNameMessage updateTravelNameMessage) {
        travelCache.setTitle(updateTravelNameMessage.title());
        return travelCache;
    }



    //엔티티를 캐시로 바꾸는 로직================================================================================
    public TravelCache createTravelCache(Travel travel,
                                         List<TravelLocation> travelLocations,
                                         List<TravelPreparation> travelPreparations) {
        return TravelCache.of(
                travel.getCode(),
                travel.getName(),
                travel.getContent(),
                travel.getStartDate(),
                travel.getEndDate(),
                travel.getCreatedAt(),
                travel.getDeletedAt(),
                getSchedule(travelLocations),
                getPreparation(travelPreparations),
                travel.getImageUrl());
    }

    private List<PreparationMessage> getPreparation(List<TravelPreparation> travelPreparations) {
        List<PreparationMessage> preparationMessages = new ArrayList<>();
        for(TravelPreparation travelPreparation : travelPreparations){
            preparationMessages.add(new PreparationMessage(travelPreparation.getCode(), travelPreparation.getName(), travelPreparation.getQuantity(), false));
        }
        return preparationMessages;
    }

    private List<ScheduleMessage> getSchedule(List<TravelLocation> travelLocations) {
        List<ScheduleMessage> schedule = new ArrayList<>();
        List<TimeLineMessage> timeLines = new ArrayList<>();
        LocalDate tempDate = travelLocations.get(0).getScheduleDetails().getStartTime().toLocalDateTime().toLocalDate();
        for(TravelLocation travelLocation : travelLocations){
            if(!tempDate.isEqual(travelLocation.getScheduleDetails().getStartTime().toLocalDateTime().toLocalDate())){
                schedule.add(new ScheduleMessage(tempDate, timeLines));
                timeLines = new ArrayList<>();
                tempDate = travelLocation.getScheduleDetails().getStartTime().toLocalDateTime().toLocalDate();
                timeLines.add(new TimeLineMessage(travelLocation.getTravelCode(), travelLocation.getScheduleDetails().getStartTime(), travelLocation.getScheduleDetails().getName(), travelLocation.getScheduleDetails().getContent(), travelLocation.getScheduleDetails().getGeographicDetails().getLongitude(), travelLocation.getScheduleDetails().getGeographicDetails().getLatitude()));
                continue;
            }
            timeLines.add(new TimeLineMessage(travelLocation.getTravelCode(), travelLocation.getScheduleDetails().getStartTime(), travelLocation.getScheduleDetails().getName(), travelLocation.getScheduleDetails().getContent(), travelLocation.getScheduleDetails().getGeographicDetails().getLongitude(), travelLocation.getScheduleDetails().getGeographicDetails().getLatitude()));
        }
        return schedule;
    }

}

/**
 * 1. message를 캐시로 바꾸는 로직
 * 2. db의 엔티티를 캐시로 바꾸는 로직(loadTravel)
 */