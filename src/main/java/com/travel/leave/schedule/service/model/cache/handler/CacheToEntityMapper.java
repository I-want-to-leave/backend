package com.travel.leave.schedule.service.model.cache.handler;

import com.travel.leave.schedule.controller.socket.messageFormat.preparation.PreparationMessage;
import com.travel.leave.schedule.controller.socket.messageFormat.timeline.ScheduleMessage;
import com.travel.leave.schedule.controller.socket.messageFormat.timeline.TimeLineMessage;
import com.travel.leave.schedule.service.model.cache.TravelCache;
import com.travel.leave.travel.entity.GeographicDetails;
import com.travel.leave.travel.entity.ScheduleDetails;
import com.travel.leave.travel.entity.Travel;
import com.travel.leave.travel.entity.TravelLocation;
import com.travel.leave.travel.entity.TravelPreparation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CacheToEntityMapper {
    public Travel mapToTravel(TravelCache travelCache) {
        return Travel.of(
                travelCache.getTravelCode(),
                travelCache.getTitle(),
                travelCache.getInformation(),
                travelCache.getStartDate(),
                travelCache.getEndDate(),
                travelCache.getCreatedAt(),
                travelCache.getDeletedAt(),
                travelCache.getImageUrl().get(0));
    }

    public List<TravelLocation> mapToTravelLocation(List<ScheduleMessage> schedule) {
        return getTravelLocations(schedule);
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

    private List<TravelLocation> getTravelLocations(List<ScheduleMessage> schedules) {
        List<TravelLocation> travelLocations = new ArrayList<>();
        for(ScheduleMessage schedule : schedules){
            for(int i = 1; i <= schedule.timeLines().size(); i++){
                TimeLineMessage tempTimeLine = schedule.timeLines().get(i-1);
                if(i == schedule.timeLines().size()){
                    travelLocations.add(TravelLocation.of(
                            tempTimeLine.id(), ScheduleDetails.of(tempTimeLine.title(), tempTimeLine.content(), tempTimeLine.time(), tempTimeLine.time(), i,
                            GeographicDetails.of(tempTimeLine.longitude(), tempTimeLine.latitude()))));
                }
                TimeLineMessage nextTimeLine = schedule.timeLines().get(i);
                travelLocations.add(TravelLocation.of(
                        tempTimeLine.id(), ScheduleDetails.of(tempTimeLine.title(), tempTimeLine.content(), tempTimeLine.time(), nextTimeLine.time(), i,
                                GeographicDetails.of(tempTimeLine.longitude(), tempTimeLine.latitude()))));
            }
        }
        return travelLocations;
    }
}
