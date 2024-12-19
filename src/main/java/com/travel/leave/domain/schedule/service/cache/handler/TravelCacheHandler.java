package com.travel.leave.domain.schedule.service.cache.handler;

import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.preparation.UpdateTravelPreparationMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline.UpdateTravelLocationGeographicMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline.UpdateTravelLocationMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.travel.UpdateTravelContentMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.travel.UpdateTravelNameMessage;
import com.travel.leave.domain.schedule.service.cache.TravelCache;

public interface TravelCacheHandler {
    void updateTravelContent(UpdateTravelContentMessage updateTravelContentMessage);

    void updateTravelPreparation(UpdateTravelPreparationMessage updateTravelPreparationMessage);

    void updateTravelLocation(UpdateTravelLocationMessage updateTravelLocationMessage);

    void updateTravelName(UpdateTravelNameMessage updateTravelNameMessage);

    void updateTravelLocationGeographic(UpdateTravelLocationGeographicMessage updateTravelLocationGeographicMessage);

    boolean hasTravel(Long travelCode);

    TravelCache loadTravel(Long travelCode, boolean hasTravelCache);

    void triggerUpdate();
}

/*
 *     private String updateTravelContent(String message) throws JsonProcessingException {
 *         travelCacheHandler.updateTravelContent(messageFormatMapper.mapToUpdateTravelContentMessage(message));
 *         return message;
 *     }
 *
 *     private String updateTravelName(String message){
 *         travelCacheHandler.updateTravelName(messageFormatMapper.mapToUpdateTravelNameMessage(message));
 *         return message;
 *     }
 *
 *     private String updateTravelPreparation(String message) {
 *         travelCacheHandler.updateTravelPreparation(messageFormatMapper.mapToUpdateTravelPreparationMessage(message));
 *         return message;
 *     }
 *
 *     private String updateTravelLocation(String message) throws JsonProcessingException {
 *         travelCacheHandler.updateTravelLocation(messageFormatMapper.mapToUpdateTravelLocationMessage(message));
 *         return message;
 *     }
 *
 *     private String updateTravelLocationGeoGraph(String message) {
 *         travelCacheHandler.updateTravelLocationGeoGraph(messageFormatMapper.mapToUpdateTravelLocationGeoGraphicMessage(message));
 *         return message;
 *     }
 */
