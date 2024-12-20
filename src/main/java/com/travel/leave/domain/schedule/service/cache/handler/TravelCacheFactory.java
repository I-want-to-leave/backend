package com.travel.leave.domain.schedule.service.cache.handler;

import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline.GeoGraphicMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline.TimeLineMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline.UpdateTravelLocationGeographicMessage;
import com.travel.leave.domain.schedule.service.cache.TravelCache;
import com.travel.leave.subdomain.travel.entity.Travel;
import com.travel.leave.subdomain.travellocaion.entity.TravelLocation;
import com.travel.leave.subdomain.travelpreparation.entity.TravelPreparation;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.preparation.PreparationMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.preparation.UpdateTravelPreparationMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline.ScheduleMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline.UpdateTravelLocationMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.travel.UpdateTravelContentMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.travel.UpdateTravelNameMessage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TravelCacheFactory {

    //소켓 message 를 캐시로 바꾸는 로직-============================================================================
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

    public TravelCache createUpdatedTravelLocationGeograhpic(TravelCache travelCache,
                                                             UpdateTravelLocationGeographicMessage updateTravelLocationGeographicMessage) {
        travelCache.setGeographicMessages(updateTravelLocationGeographicMessage.geoGraphicMessages());
        return travelCache;
    }


    //엔티티를 캐시로 바꾸는 로직(loadTravel 사용됨)
    public TravelCache createTravelCache(Travel travel,
                                         List<TravelLocation> travelLocations,
                                         List<TravelPreparation> travelPreparations,
                                         List<String> userNickNames) {
        return TravelCache.builder()
                .travelCode(travel.getCode())
                .title(travel.getName())
                .information(travel.getContent())
                .startDate(travel.getStartDate().toLocalDate())
                .endDate(travel.getEndDate().toLocalDate())
                .createdAt(travel.getCreatedAt())
                .deletedAt(travel.getDeletedAt())
                .schedule(getSchedule(travelLocations))
                .geographicMessages(getGeographicMessages(travelLocations))
                .preparation(getPreparation(travelPreparations))
                .imageUrl(List.of(travel.getImageUrl()))
                .isDeleted(false)
                .usernames(userNickNames)
                .build();
    }

    private List<ScheduleMessage> getSchedule(List<TravelLocation> travelLocations) {
        List<ScheduleMessage> scheduleMessages = new ArrayList<>();
        LocalDate tempLocationDate = travelLocations.get(0)
                .getScheduleDetails().getStartTime().toLocalDateTime().toLocalDate();
        List<TimeLineMessage> timeLines = new ArrayList<>();
        for(TravelLocation travelLocation : travelLocations) {
            if(!travelLocation.getScheduleDetails().getStartTime().toLocalDateTime().toLocalDate().isEqual(tempLocationDate)){
                scheduleMessages.add(new ScheduleMessage(tempLocationDate, timeLines));
                tempLocationDate = travelLocation.getScheduleDetails().getStartTime().toLocalDateTime().toLocalDate();
                timeLines = new ArrayList<>();
            }
            timeLines.add(
                    new TimeLineMessage(
                            travelLocation.getTravelCode(),
                            travelLocation.getScheduleDetails().getStartTime().toString().substring(11, 16),
                            travelLocation.getScheduleDetails().getName(),
                            travelLocation.getScheduleDetails().getContent()));
        }
        scheduleMessages.add(new ScheduleMessage(tempLocationDate, timeLines));
        return scheduleMessages;
    }

    private List<GeoGraphicMessage> getGeographicMessages(List<TravelLocation> travelLocations) {
        List<GeoGraphicMessage> geoGraphicMessages = new ArrayList<>();
        for(TravelLocation travelLocation : travelLocations) {
            if(travelLocation.getScheduleDetails().getGeographicDetails() == null){
                continue;
            }
            geoGraphicMessages.add(
                    new GeoGraphicMessage(
                            travelLocation.getTravelCode(),
                            travelLocation.getScheduleDetails().getGeographicDetails().getLongitude(),
                            travelLocation.getScheduleDetails().getGeographicDetails().getLatitude()));
        }
        return geoGraphicMessages;
    }

    private List<PreparationMessage> getPreparation(List<TravelPreparation> travelPreparations) {
        List<PreparationMessage> preparationMessages = new ArrayList<>();
        for(TravelPreparation travelPreparation : travelPreparations){
            preparationMessages.add(new PreparationMessage(travelPreparation.getCode(), travelPreparation.getName(), travelPreparation.getQuantity(), false));
        }
        return preparationMessages;
    }
}

/*
 * 1. message 를 캐시로 바꾸는 로직
 * 2. db의 엔티티를 캐시로 바꾸는 로직(loadTravel)
 */

/*
new TimeLineMessage(travelLocation.getTravelCode(), travelLocation.getScheduleDetails().getStartTime(), travelLocation.getScheduleDetails().getName(), travelLocation.getScheduleDetails().getContent(), null, null)
 */