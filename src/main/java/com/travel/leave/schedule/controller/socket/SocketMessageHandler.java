package com.travel.leave.schedule.controller.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.travel.leave.schedule.controller.socket.messageFormat.MessageFormatMapper;
import com.travel.leave.schedule.service.ScheduleService;
import com.travel.leave.schedule.service.model.cache.handler.TravelCacheHandler;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SocketMessageHandler {
    private final TravelCacheHandler travelCacheHandler;
    private final ScheduleService scheduleService;
    private final MessageFormatMapper messageFormatMapper;

    private final Map<String, Function<String, String>> serviceHandlers = new HashMap<>();

    @SneakyThrows
    @PostConstruct
    public void init() {
        serviceHandlers.put(ServiceType.UPDATE_TRAVEL_LOCATION.getMethodName(), this::updateTravelLocation);
        serviceHandlers.put(ServiceType.UPDATE_TRAVEL_CONTENT.getMethodName(), this::updateTravelContent);
        serviceHandlers.put(ServiceType.UPDATE_TRAVEL_PREPARATION.getMethodName(), this::updateTravelPreparation);
        serviceHandlers.put(ServiceType.UPDATE_TRAVEL_NAME.getMethodName(), this::updateTravelName);
    }
    //실제 실행 메서드=======================================================================
    public String progress(String message) throws JsonProcessingException {
        Long travelCode = messageFormatMapper.getTravelCode(message);
        String serviceType = messageFormatMapper.getServiceType(message);

        Function<String, String> handler = serviceHandlers.get(serviceType);
        loadTravel(travelCode);
        return handler.apply(message);
    }
    //====================================================================================

    private void loadTravel(Long travelCode) {  //업데이트 전 캐시에 데이터가 있는지 확인한다.
        if(!travelCacheHandler.hasTravel(travelCode)){
            scheduleService.loadTravel(travelCode);
        }
    }

    //message를 캐시에 맞게 매핑하는 과정 + 실행
    private String updateTravelContent(String message) {
        travelCacheHandler.updateTravelContent(messageFormatMapper.mapToUpdateTravelContentMessage(message));
        return message;
    }

    private String updateTravelName(String message){
        travelCacheHandler.updateTravelName(messageFormatMapper.mapToUpdateTravelNameMessage(message));
        return message;
    }

    private String updateTravelPreparation(String message) {
        travelCacheHandler.updateTravelPreparation(messageFormatMapper.mapToUpdateTravelPreparationMessage(message));
        return message;
    }

    private String updateTravelLocation(String message) {
        travelCacheHandler.updateTravelLocation(messageFormatMapper.mapToUpdateTravelLocationMessage(message));
        return message;
    }
}
