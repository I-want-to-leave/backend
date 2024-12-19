package com.travel.leave.domain.schedule.controller.socket.messageFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.preparation.UpdateTravelPreparationMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline.UpdateTravelLocationMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline.UpdateTravelLocationGeographicMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.travel.UpdateTravelContentMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.travel.UpdateTravelNameMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageFormatMapper {
    private final ObjectMapper objectMapper;

    public Long getTravelCode(String message) throws JsonProcessingException {
        JsonNode node = objectMapper.readTree(message);
        return node.get("travelCode").asLong();
    }

    public String getServiceType(String message) throws JsonProcessingException {
        JsonNode node = objectMapper.readTree(message);
        return node.get("serviceType").asText();
    }


    public UpdateTravelPreparationMessage mapToUpdateTravelPreparationMessage(String message){
        return objectMapper.convertValue(message, UpdateTravelPreparationMessage.class);
    }

    public UpdateTravelContentMessage mapToUpdateTravelContentMessage(String message){
        return objectMapper.convertValue(message, UpdateTravelContentMessage.class);
    }

    public UpdateTravelNameMessage mapToUpdateTravelNameMessage(String message){
        return objectMapper.convertValue(message, UpdateTravelNameMessage.class);
    }

    public UpdateTravelLocationMessage mapToUpdateTravelLocationMessage(String message){
        return objectMapper.convertValue(message, UpdateTravelLocationMessage.class);
    }

    public UpdateTravelLocationGeographicMessage mapToUpdateTravelLocationGeographic(String message) {
        return objectMapper.convertValue(message, UpdateTravelLocationGeographicMessage.class);
    }
}
